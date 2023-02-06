
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controllers.implement;

import cataovo.constants.Constants;
import cataovo.controllers.MainPageController;
import cataovo.entities.Frame;
import cataovo.entities.Point;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import cataovo.resources.MainResources;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

/**
 * Controls the actions with the Frames
 *
 * @author bibil
 */
public class MainPageControllerImplements implements MainPageController {

    private static final Logger LOG = Logger.getLogger(MainPageControllerImplements.class.getName());
    private int frameCounter = 0;

    public MainPageControllerImplements() {
        frameCounter = 0;
    }

    /**
     * Heads to the next frame in Manual.
     *
     * @param parentName
     * @param parent
     * @throws ImageNotValidException
     * @throws DirectoryNotValidException
     */
    @Override
    public void toNextFrame(JLabel parentName, JLabel parent) throws ImageNotValidException, DirectoryNotValidException {
        if (!MainResources.getInstance().getPalette().getFrames().isEmpty()) {
            Frame frame = MainResources.getInstance().getPalette().getFrames().poll();
            MainResources.getInstance().setCurrentFrame(frame);
            showFrameOnScreen(parentName, parent, frame);
        } else {
            LOG.log(Level.INFO, "The Palette was completed!");
            parentName.setText(Constants.NO_PALETTE_SELECTED);
            parent.setIcon(null);
            parent.setBorder(null);
        }
    }

    /**
     *
     * @param parentName
     * @param jTabbedPane
     * @throws ImageNotValidException
     * @throws DirectoryNotValidException
     */
    @Override
    public void toNextFrame(JLabel parentName, JTabbedPane jTabbedPane) throws ImageNotValidException, DirectoryNotValidException, ArrayIndexOutOfBoundsException {
        // Verificar as palavras-chave que formam o caminho de um diretório do processamento automático no projeto: (nome da paleta) e "auto". 
        if (!MainResources.getInstance().getSavingFolder().getAbsolutePath().contains(MainResources.getInstance().getPalette().getDirectory().getName())
                || !MainResources.getInstance().getSavingFolder().getAbsolutePath().contains("auto")) {
            StringBuilder sb = new StringBuilder("É possível que uma nova paleta ou nenhuma tenha sido selecionada, portanto o diretório de salvamento foi alterado ou ainda não existe.");
            sb.append(Constants.QUEBRA_LINHA);
            sb.append("Inicie um novo processamento para assim poder visualizar os devidos resultados.");
            throw new DirectoryNotValidException(sb.toString());
        } else {
            // Listando os diretórios correspondentes a cada frame
            File[] frameResults = MainResources.getInstance().getSavingFolder().listFiles((pathname) -> pathname.isDirectory());
            if (frameResults.length > 0) {
                if (this.frameCounter < (frameResults.length - 1)) {
                    addOneAtFrameCounting();
                    // Buscar o diretório correspondente ao frame atual
                    File currentFrameDirectory = frameResults[this.frameCounter];
                    Frame current = putFileOnFrame(jTabbedPane, currentFrameDirectory, parentName);
                    MainResources.getInstance().setCurrentFrame(current);
                    LOG.log(Level.INFO, "Image Position: {0}", this.frameCounter);
                } else {
                    this.frameCounter = frameResults.length - 1;
                    throw new ArrayIndexOutOfBoundsException("Atingiu o fim da paleta");
                }
            } else {
                throw new DirectoryNotValidException("Não foram encontradas as pastas correspondentes ao processamento automático");
            }
        }
    }

    @Override
    public void toPreviousFrame(JLabel parentName, JTabbedPane jTabbedPane) throws ImageNotValidException, DirectoryNotValidException, ArrayIndexOutOfBoundsException {
        // Verificar as palavras-chave que formam o caminho de um diretório do processamento automático no projeto: (nome da paleta) e "auto". 
        if (!MainResources.getInstance().getSavingFolder().getAbsolutePath().contains(MainResources.getInstance().getPalette().getDirectory().getName())
                || !MainResources.getInstance().getSavingFolder().getAbsolutePath().contains("auto")) {
            StringBuilder sb = new StringBuilder("Inicie um novo processamento para assim poder visualizar os devidos resultados.");
            sb.append(Constants.QUEBRA_LINHA);
            sb.append("Altere a pasta de destino para '../cataovo/(nome-da-paleta)/auto/(data-e-hora-da-análise)' ou inicie um novo processamento e assim ver os resultados do processamento automático.");
            throw new DirectoryNotValidException(sb.toString());
        } else {
            // Listando os diretórios correspondentes a cada frame
            File[] frameResults = MainResources.getInstance().getSavingFolder().listFiles((pathname) -> pathname.isDirectory());
            if (frameResults.length > 0) {
                if (this.frameCounter > 0) {
                    subOneAtFrameCounting();
                    // Buscar o diretório correspondente ao frame atual
                    File currentFrameDirectory = frameResults[this.frameCounter];
                    Frame current = putFileOnFrame(jTabbedPane, currentFrameDirectory, parentName);
                    MainResources.getInstance().setCurrentFrame(current);
                    LOG.log(Level.INFO, "Image Position: {0}", this.frameCounter);
                } else {
                    this.frameCounter = 0;
                    throw new ArrayIndexOutOfBoundsException("Atingiu o início da paleta");
                }
            } else {
                throw new DirectoryNotValidException("Não foram encontradas as pastas correspondentes ao processamento automático");
            }
        }
    }

   /**
     *
     * @param jTabbedPane
     * @param currentFrameDirectory
     * @param current
     * @param parentName
     * @return
     * @throws ImageNotValidException
     */
    private Frame putFileOnFrame(JTabbedPane jTabbedPane, File currentFrameDirectory, JLabel parentName) throws ImageNotValidException {
        // Recuperar as imagens resultantes e inserí-las nas abas correspondentes
        Frame current = new Frame();
        for (int i = 0; i < jTabbedPane.getComponents().length; i++) {
            Component component = jTabbedPane.getComponents()[i];
            Frame frame = new Frame(currentFrameDirectory.listFiles()[i].getAbsolutePath());
            if (frame.getName().equalsIgnoreCase("original")) {
                current = frame;
            }
            // Posicionar as imagens nos componentes
            JLabel jlabel = (JLabel) component.getComponentAt(0, 0);
            presentImageFrameOnScreen(jlabel, parentName, frame);
        }
        return current;
    }

    /**
     * Setts the icons related to each JLabel (image or name)
     *
     * @param parentName
     * @param parent
     * @param frame
     * @return
     */
    private boolean showFrameOnScreen(JLabel parentName, JLabel parent, Object frame) throws ImageNotValidException {
        if (frame instanceof Frame fr) {
            LOG.log(Level.INFO, "Presenting the image {0} on screen...", fr.getName());
            if (presentImageFrameOnScreen(parent, parentName, fr)) {
                return true;
            }
        } else if (frame instanceof Icon icon) {
            presentImageIconOnScreen(parent, icon);
            return true;
        }

        return false;
    }

    /**
     *
     * @param parent
     * @param icon
     */
    private void presentImageIconOnScreen(JLabel parent, Icon icon) {
        parent.setIcon(icon);
    }

    /**
     *
     * @param parent
     * @param parentName
     * @param fr
     * @return
     */
    private boolean presentImageFrameOnScreen(JLabel parent, JLabel parentName, Frame fr) {
        if (fr.getPaletteFrame() != null && fr.getPaletteFrame().exists()) {
            File f = (File) fr.getPaletteFrame();
            parent.setIcon(showImageFile(f));
            setLabelText(parentName, fr.getName());
            return true;
        }
        return false;
    }

    /**
     *
     * @param parentName
     */
    private void setLabelText(JLabel parentName, String text) {
        parentName.setText(text);
    }

    /**
     * Receives a file and shows its image at jLabelImage.
     *
     * @param file the File image
     */
    private ImageIcon showImageFile(File file) {
        return new ImageIcon(file.getPath());
    }

    /**
     * When a Frame was finished its analysis, go to next Frame on Queue.
     *
     * @param jLabel1
     * @param jLabel2
     * @throws cataovo.exceptions.ImageNotValidException
     * @throws cataovo.exceptions.DirectoryNotValidException
     */
    @Override
    public void onFrameFinished(JLabel jLabel1, JLabel jLabel2) throws ImageNotValidException, DirectoryNotValidException {
        LOG.log(Level.INFO, "The frame was analysed. Charging next...");
        MainResources.getInstance().getPaletteToSave().getFrames()
                .offer(MainResources.getInstance().getCurrentFrame());
        toNextFrame(jLabel1, jLabel2);
    }

    /**
     * From a given tab, it shows the frames selected
     *
     * @param tabComponent
     * @param parentNameLabel
     * @param parentLabel
     * @throws ImageNotValidException
     * @throws DirectoryNotValidException
     */
    @Override
    public void showFramesOnSelectedTabScreen(Component tabComponent, JLabel parentNameLabel, JLabel parentLabel, Object frame) throws ImageNotValidException, DirectoryNotValidException, UnsupportedOperationException, AssertionError {
        if (tabComponent instanceof JTabbedPane jTabbedPane) { // if the component is another type of component just add another conditional
            switch (jTabbedPane.getSelectedIndex()) {
                case 0 -> {
                    parentLabel.setText(null);
                    parentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    parentLabel.setVisible(true);
                    this.frameCounter = 0;
                    showFrameOnScreen(parentNameLabel, parentLabel, frame);
                }
                case 1 -> {
                    this.frameCounter = -1;
                    showFrameOnScreen(parentNameLabel, parentLabel, frame);
                }
                default ->
                    throw new AssertionError("No tab with such index");
            }
        } else {
            throw new UnsupportedOperationException("Operation not supported with such component.");
        }
    }

    @Override
    public void showSubFrameOnSelectedTabScreen(JLabel subframeNameLabel, JLabel subframeLabel, Icon frame, Point point) throws DirectoryNotValidException {
        subframeLabel.setText(null);
        presentImageIconOnScreen(subframeLabel, frame);
        String text = "(" + point.getX() + ", " + point.getY() + ")";
        setLabelText(subframeNameLabel, text);
    }

    public int getFrameCounter() {
        return frameCounter;
    }

    public void addOneAtFrameCounting() {
        frameCounter++;
    }

    public void subOneAtFrameCounting() {
        frameCounter--;
    }

}
