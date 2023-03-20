/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.constants;

/**
 * Constants used in the project.
 *
 * @author Bianca Leopoldo Ramos
 */
public class Constants {

    /**
     * ACTION COMMAND ABRIR PASTA
     */
    public static final String ITEM_ACTION_COMMAND_OPEN_PALETTE_PT_BR = "Abrir Paleta";
    /**
     * ACTION COMMAND SELECIONAR PASTA DESTINO
     */
    public static final String ITEM_ACTION_COMMAND_SELECT_DESTINATION_FOLDER_PT_BR = "Selecionar Pasta Destino";
    /**
     * ACTION COMMAND SALVAR ARQUIVO FINAL
     */
    public static final String ITEM_ACTION_COMMAND_SAVE_MANUAL_REPORT_PT_BR = "Salvar Arquivo";
    /**
     * ACTION COMMAND SELECIONAR RELATORIO
     */
    public static final String ITEM_ACTION_COMMAND_SELECT_REPORT_PT_BR = "Selecionar Relatório";

    /**
     * TAB NAME MANUAL
     */
    public static final String TAB_NAME_MANUAL_PT_BR = "Manual";
    /**
     * TAB NAME AUTOMATICO
     */
    public static final String TAB_NAME_AUTOMATIC_PT_BR = "Automático";
    /**
     * TAB NAME AVALIACAO
     */
    public static final String TAB_NAME_EVALUATION_PT_BR = "Avaliação";

    /**
     * NO PALETTE SELECTED
     */
    public static final String NO_PALETTE_SELECTED = "Escolha uma pasta";

    /**
     * QUEBRA DE LINHA
     */
    public static final String QUEBRA_LINHA = "\n";

    /**
     * ORIGINAL.PNG
     */
    public static final String ORIGINAL_PNG = "/original.png";
    /**
     * BLUR.PNG
     */
    public static final String BLUR_PNG = "/blur.png";
    /**
     * BINARY.PNG
     */
    public static final String BINARY_PNG = "/binary.png";
    /**
     * MORPH.PNG
     */
    public static final String MORPH_PNG = "/morph.png";
    /**
     * CONTOURS.PNG
     */
    public static final String CONTOURS_PNG = "/contours.png";

    /**
     * NAME MANUAL
     */
    public static final String NAME_MANUAL = "manual";
    /**
     * NAME AUTOMATICO
     */
    public static final String NAME_AUTOMATICO = "auto";
    /**
     * NAME AVALIACAO
     */
    public static final String NAME_AVALIACAO = "resut";

    public static final int STEPS_TO_POINT_SAVING = 5;

    public static final int FRAME_SLOT_TO_PROCESS_ON_PALETTE = 5;

    /**
     * Custom error message to REGION NOT VALID
     */
    public static String ERROR_REGION_NOT_VALID_ENG_1 = "Axis(ies) with difference too small (less than 35px) or too large (more than 110px)";

    /**
     * Custom error message to DIRECTORY NOT VALID
     */
    public static String ERROR_DIRECTORY_NOT_VALID_ENG_1 = "The file path does not represent a valid directory";

    /**
     * Custom error message to IMAGE NOT VALID
     */
    public static String ERROR_IMAGE_NOT_VALID_ENG_1 = "The file does not represent a valid image: *.jpg or *.png";

    /**
     * Custom error message to TAB NOT VALID TO EVALUATE
     */
    public static String ERROR_TAB_NOT_VALID_TO_EVALUATE_ENG_1 = "Must select Evaluation tab";
    
    /**
     * MINUMUM AXIS X DISTANCE ON REGION
     */
    public static int MINUMUM_AXIS_X_DISTANCE_ON_REGION = 35;
    
    /**
     * MINUMUM AXIS Y DISTANCE ON REGION
     */
    public static int MINUMUM_AXIS_Y_DISTANCE_ON_REGION = 35;
    
    /**
     * MAXIMUM AXIS X DISTANCE ON REGION
     */
    public static int MAXIMUM_AXIS_X_DISTANCE_ON_REGION = 110;
    
    /**
     * MAXIMUM AXIS Y DISTANCE ON REGION
     */
    public static int MAXIMUM_AXIS_Y_DISTANCE_ON_REGION = 110;

}
