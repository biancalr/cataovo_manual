/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cataovo.enums;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public enum ProcessingMode {
    
    MANUAL("manual"), AUTOMATIC("auto"), EVALUATION("result");
    
    private String processingMode;

    private ProcessingMode(String processingMode) {
        this.processingMode = processingMode;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(String processingMode) {
        this.processingMode = processingMode;
    }
    
}
