/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.controllers.implement;

import cataovo.constants.Constants;
import cataovo.controllers.FileReaderController;
import cataovo.entities.Point;
import cataovo.entities.Region;
import cataovo.externals.fileHandlers.readers.Reader;
import cataovo.externals.fileHandlers.readers.csv.csvReader.CsvFileReader;
import cataovo.externals.libs.opencvlib.wrappers.PointWrapper;
import cataovo.externals.libs.opencvlib.wrappers.RectWrapper;
import java.io.FileNotFoundException;
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

    private final Reader csvFileReader;

    public FileReaderControllerImplements() {
        csvFileReader = new CsvFileReader();
    }

    @Override
    public List<RectWrapper> getRegionsInFrameFile(String frameName, String report) throws FileNotFoundException, NumberFormatException {
        List<RectWrapper> regions = new ArrayList<>();

        try {
            Optional<String> optLine = csvFileReader.readLine(frameName, report);

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
        }
        LOG.log(Level.INFO, "Regions size: {0}", regions.size());
        return regions;
    }

    @Override
    public List<PointWrapper> getPointsInFrameFile(String frameName, String report) throws FileNotFoundException, NumberFormatException {
        List<PointWrapper> points = new ArrayList<>();

        try {

            // encontrando o frame filtrando pelo nome
            Optional<String> optLine = csvFileReader.readLine(frameName, report);

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
        } 
        LOG.log(Level.INFO, "Points size: {0}", points.size());
        return points;
    }

    @Override
    public StringBuilder readFullFileContent(String report) {
        StringBuilder builder = new StringBuilder();
        // remove as linhas que não contém necessariamente os pontos e as regiões
        csvFileReader.readContent(report).forEach((line) -> {
            builder.append(line);
            builder.append(Constants.QUEBRA_LINHA);
        });

        return builder;
    }

}
