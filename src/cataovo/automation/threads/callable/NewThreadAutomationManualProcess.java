/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.callable;

import cataovo.entities.Palette;
import cataovo.filechooser.handler.FileExtension;

/**
 *
 * @author bianc
 */
public class NewThreadAutomationManualProcess extends NewThreadAutomation{

    public NewThreadAutomationManualProcess(Palette palette, String savingDirectory, FileExtension fileExtension, String parent) {
        super(palette, savingDirectory, fileExtension, parent);
    }

    @Override
    protected StringBuffer createContent() {
        StringBuffer sb = new StringBuffer(getPalette().getDirectory().getPath());
        sb.append("|");
        sb.append(getPalette().getTheTotalNumberOfEggsPalette());
        getPalette().getFrames().stream().forEach((f) -> {
            sb.append("|");
            sb.append(f.getName());
            if (!f.getRegionsContainingEggs().isEmpty()) {
                f.getRegionsContainingEggs().stream().map((r) -> {
                    sb.append(",");
                    sb.append(r.getInitialPoint().getX());
                    return r;
                }).map((r) -> {
                    sb.append(",");
                    sb.append(r.getInitialPoint().getY());
                    return r;
                }).map((r) -> {
                    sb.append(",");
                    sb.append(r.getWidth());
                    return r;
                }).forEachOrdered((r) -> {
                    sb.append(",");
                    sb.append(r.getHeight());
                });
            }
        });
        return sb;
    }
}
