package fr.plage.reservation.business;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class File {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;
	private byte numero;
	private double prixJournalier;
	@OneToMany(mappedBy = "file")
	private List<Parasol> parasols;

}

