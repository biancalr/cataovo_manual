/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.controllers.implement;

import cataovo.constants.Constants;
import cataovo.controllers.FileReaderController;
import cataovo.entities.Point;
import cataovo.entities.Region;
import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.opencvlib.wrappers.RectWrapper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class FileReaderControllerImplements implements FileReaderController {

    private static final Logger LOG = Logger.getLogger(FileReaderControllerImplements.class.getName());

    @Override
    public List<RectWrapper> getRegionsInFrameFile(String frameName, String report) throws FileNotFoundException, NumberFormatException {
        List<RectWrapper> regions = new ArrayList<>();

        try (InputStreamReader in = new InputStreamReader(new FileInputStream(report)); BufferedReader csvReader = new BufferedReader(in)) {
            Optional<String> optLine = csvReader.lines().filter((line) -> line.contains(frameName)).findFirst();

            optLine.ifPresent(line -> {

                LOG.log(Level.INFO, "LINE: {0}", optLine.get());

                String[] data = line.split(",");
                if (data.length > 2) {
                    // ignorando posição 0 pois representa o nome do frame, o qual não é necessário
                    for (int i = 1; i < data.length; i += 4) {
                        String string = data[i];
                        if (string != null) {
                            RectWrapper current = new RectWrapper(
                                    new Region(
                                            Integer.parseInt(data[i + 3]),
                                            Integer.parseInt(data[i + 2]),
                                            new Point(
                                                    Integer.parseInt(data[i]),
                                                    Integer.parseInt(data[i + 1]))));
                            regions.add(current);
                        }
                    }
                }

            });

        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new NumberFormatException("Error while converting a data string to number");
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new FileNotFoundException(ex.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        LOG.log(Level.INFO, "Regions size: {0}", regions.size());
        return regions;
    }

    @Override
    public List<PointWrapper> getPointsInFrameFile(String frameName, String report) throws FileNotFoundException, NumberFormatException {
        List<PointWrapper> points = new ArrayList<>();

        try (InputStreamReader in = new InputStreamReader(new FileInputStream(report)); BufferedReader csvReader = new BufferedReader(in)) {

            // encontrando o frame filtrando pelo nome
            Optional<String> optLine = csvReader.lines().filter((line) -> line.contains(frameName)).findFirst();

            LOG.log(Level.INFO, "LINE: {0}", optLine.get());

            optLine.ifPresent(line -> {

                String[] data = line.split(",");
                if (data.length > 3) {
                    // ignorando posição 0 pois representa o nome do frame, o qual não é necessário
                    // ignorando a posição 1 pois ela representa a quantidade de ovos no frame, o qual também não é necessário
                    // Não é necessário ler todos os pontos salvos ou pode poluir o visual do frame por isso i pula 4
                    for (int i = 2; i < data.length; i += 4) {
                        String string = data[i];
                        if (string != null) {
                            PointWrapper current = new PointWrapper(
                                    new Point(
                                            Integer.parseInt(data[i].replace(".0", "").trim()),
                                            Integer.parseInt(data[i + 1].replace(".0", "").trim())));
                            points.add(current);
                        }
                    }

                }
            });

        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new NumberFormatException("Error while converting a data string to number");
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new FileNotFoundException(ex.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        LOG.log(Level.INFO, "Points size: {0}", points.size());
        return points;
    }

    @Override
    public StringBuilder readFullFileContent(String report) throws FileNotFoundException {
        StringBuilder builder = new StringBuilder();
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(report)); BufferedReader csvReader = new BufferedReader(in)) {
            // remove as linhas que não contém necessariamente os pontos e as regiões
            csvReader.lines().filter((line) -> !line.contains("C:") && line.length() > 5 && !line.isBlank()).forEach((l) -> {
                builder.append(l);
                builder.append(Constants.QUEBRA_LINHA);
            });

            LOG.log(Level.INFO, report + " size {0}", csvReader.lines().count());

        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new FileNotFoundException();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return builder;
    }

}
