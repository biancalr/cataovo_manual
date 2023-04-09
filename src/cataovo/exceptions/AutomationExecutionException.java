/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package cataovo.exceptions;

/**
 *
 * @author bianc
 */
public class AutomationExecutionException extends Exception {

    /**
     * Creates a new instance of <code>FrameAutomationExecutionException</code>
     * without detail message.
     */
    public AutomationExecutionException() {
    }

    /**
     * Constructs an instance of <code>FrameAutomationExecutionException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AutomationExecutionException(String msg) {
        super(msg);
    }
}
