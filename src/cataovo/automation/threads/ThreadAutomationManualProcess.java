/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.automation.threads;

import cataovo.entities.Palette;
import cataovo.filechooser.handler.FileExtension;

/**
 *
 * @author bibil
 */
public class ThreadAutomationManualProcess extends ThreadAutomation{

    public ThreadAutomationManualProcess(Palette palette, String savingDirectory, FileExtension extension, String parent) {
        super(palette, savingDirectory, extension, parent);
    }

    @Override
    protected StringBuffer createContent() {
        StringBuffer sb = new StringBuffer(getPalette().getDirectory().getPath());
        sb.append("|");
        sb.append(getPalette().getTheTotalNumberOfEggsPalette());
        getPalette().getFrames().stream().forEachOrdered((f) -> {
            sb.append("|");
            sb.append(f.getName());
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
        });
        return sb;
    }
    
}
