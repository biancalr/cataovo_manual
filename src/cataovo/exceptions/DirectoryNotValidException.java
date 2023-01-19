/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.exceptions;

/**
 * When the directory's not valid.
 *
 * @author Bianca Leopoldo Ramos
 */
public class DirectoryNotValidException extends Exception {

    /**
     * Creates a new instance of <code>DirectoryNotFoundException</code> without
     * detail message.
     */
    public DirectoryNotValidException() {
    }

    /**
     * Constructs an instance of <code>DirectoryNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DirectoryNotValidException(String msg) {
        super(msg);
    }
}
