package fr.plage.reservation.exception;

public class PaysInconnuException extends RuntimeException{

        private static final long serialVersionUID = 1L;

        public PaysInconnuException(String message) {
            super(message);
        }
}
