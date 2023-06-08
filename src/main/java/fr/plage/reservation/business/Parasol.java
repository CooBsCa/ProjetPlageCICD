package fr.plage.reservation.business;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Parasol {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;
	private byte numEmplacement;
	@ManyToOne
	private File file;
	@ManyToMany(mappedBy = "parasols")
	private List<Reservation> reservation;

}
