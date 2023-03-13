/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package cataovo.exceptions;

/**
 *
 * @author bianc
 */
public class RegionNotValidException extends Exception {

    /**
     * Creates a new instance of <code>RegionNotValid</code> without detail
     * message.
     */
    public RegionNotValidException() {
    }

    /**
     * Constructs an instance of <code>RegionNotValid</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public RegionNotValidException(String msg) {
        super(msg);
    }
}
