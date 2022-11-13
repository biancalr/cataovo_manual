/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.exceptions;

/**
 *
 * @author bibil
 */
public class ImageNotValidException extends Exception {

    /**
     * Creates a new instance of <code>ImageNotValidException</code> without
     * detail message.
     */
    public ImageNotValidException() {
    }

    /**
     * Constructs an instance of <code>ImageNotValidException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ImageNotValidException(String msg) {
        super(msg);
    }
}
