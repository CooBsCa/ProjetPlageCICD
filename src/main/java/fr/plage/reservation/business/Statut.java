package fr.plage.reservation.business;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Statut {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;
	@NonNull
	private String nom;
}
