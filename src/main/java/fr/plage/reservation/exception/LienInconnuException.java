package fr.plage.reservation.exception;

public class LienInconnuException extends RuntimeException{

            private static final long serialVersionUID = 1L;

            public LienInconnuException(String message) {
                super(message);
            }
}
