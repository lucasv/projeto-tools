package com.tools.prova.batch.tasklet;

import com.tools.prova.model.Cliente;
import com.tools.prova.model.Venda;
import com.tools.prova.model.Vendedor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.util.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class ImportacaoTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String inputFileName = (String) chunkContext.getStepContext().getJobParameters().getOrDefault("input.file.name", "");
        String outputFileName = (String) chunkContext.getStepContext().getJobParameters().getOrDefault("output.file.name", "");
        Assert.notNull(inputFileName, "Arquivo entrada nao encontrado");
        Assert.notNull(outputFileName, "Arquivo saida nao encontrado");

        ArrayList<Vendedor> vendedors = new ArrayList<>();
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Venda> vendas = new ArrayList<>();

        Files.lines(Paths.get(inputFileName))
                .map(s -> s.split(";"))
                .forEach(line -> {
                    switch (line[0].trim()) {
                        case "001": {
                            Vendedor vendedor = new Vendedor(line);
                            vendedors.add(vendedor);
                            break;
                        }
                        case "002": {
                            Cliente cliente = new Cliente(line);
                            clientes.add(cliente);
                            break;
                        }
                        case "003": {
                            Venda venda = new Venda(line);
                            vendas.add(venda);
                            break;
                        }
                    }
                });

        Integer qtdClientes = clientes.size();
        Integer qtdVendedores = vendedors.size();
        Integer idVendaMaisAlto = vendas
                .stream()
                .sorted(Comparator.comparing(Venda::getValorTotal).reversed())
                .map(Venda::getIdVenda)
                .findFirst()
                .get();

        String nomeVendedorMenosVendeu = vendas
                .stream()
                .collect(Collectors.groupingBy(Venda::getNomeVendedor))
                .entrySet()
                .stream()
                .min(Comparator.comparing(map -> map.getValue().size()))
                .get()
                .getKey();

        /*
            1. Quantidade de Clientes:
            2. Quantidade de Vendedores:
            3. ID da Venda de valor mais alto:
            4. Nome do Vendedor que menos vendeu:
        */

        ArrayList<String> lines = new ArrayList<>();
        lines.add("1. Quantidade de Clientes:".concat(qtdClientes.toString()));
        lines.add("2. Quantidade de Vendedores:".concat(qtdVendedores.toString()));
        lines.add("3. ID da Venda de valor mais alto:".concat(idVendaMaisAlto.toString()));
        lines.add("4. Nome do Vendedor que menos vendeu:".concat(nomeVendedorMenosVendeu));

        Files.write(Paths.get(outputFileName), lines);
        return RepeatStatus.FINISHED;
    }

}
