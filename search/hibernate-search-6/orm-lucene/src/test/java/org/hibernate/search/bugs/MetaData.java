package org.hibernate.search.bugs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class MetaData {
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	public Collection<UntypedDataEntity> getMap() {
		return map;
	}

	public void setMap(Collection<UntypedDataEntity> map) {
		this.map = map;
	}
	
	public void addData(UntypedDataEntity data) {
		map.add(data);
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKeyEnumerated(value = EnumType.ORDINAL)
	@JoinTable(joinColumns = @JoinColumn(name = "METADATA_ID"))
	@Fetch(FetchMode.SELECT)
	private Collection<UntypedDataEntity> map;

	
	public MetaData() {	
		map = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void printEntities() {
		for(UntypedDataEntity entity : map) {
			System.out.println("Entity: "+entity.toString());
		}
	}

	
	

	
}
