package fr.plage.reservation.exception;

public class ReservationInexistante extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ReservationInexistante(String message) {
        super(message);
    }

}
