package fr.plage.reservation.exception;

public class ClientDejaPresentException extends RuntimeException{

            private static final long serialVersionUID = 1L;

            public ClientDejaPresentException(String message) {
                super(message);
            }
}
