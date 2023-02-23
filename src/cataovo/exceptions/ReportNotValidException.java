/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package cataovo.exceptions;

/**
 *
 * @author bianc
 */
public class ReportNotValidException extends Exception {

    /**
     * Creates a new instance of <code>ReportNotValidException</code> without
     * detail message.
     */
    public ReportNotValidException() {
    }

    /**
     * Constructs an instance of <code>ReportNotValidException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ReportNotValidException(String msg) {
        super(msg);
    }
}
