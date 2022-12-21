/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.helper;

/**
 *
 * @author bianc
 */
public class MainPanelTabHelper {
    
    private boolean isActualTabProcessing;
    private int tabIndex;
    private String tabName;

    public MainPanelTabHelper(boolean isActualTabProcessing, int tabIndex, String tabName) {
        this.isActualTabProcessing = isActualTabProcessing;
        this.tabIndex = tabIndex;
        this.tabName = tabName;
    }

    public MainPanelTabHelper() {
    }

    public boolean isIsActualTabProcessing() {
        return isActualTabProcessing;
    }

    public void setIsActualTabProcessing(boolean isActualTabProcessing) {
        this.isActualTabProcessing = isActualTabProcessing;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    
    
}
