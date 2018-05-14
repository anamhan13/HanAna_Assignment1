package client.model;

import java.io.Serializable;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the patient database table.
 * 
 */

public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	private int pnc;

	private String address;

	private Date birtdate;

	private String name;

	private List<Consultation> consultations;

	public Patient() {
	}

	public int getPnc() {
		return this.pnc;
	}

	public void setPnc(int pnc) {
		this.pnc = pnc;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirtdate() {
		return this.birtdate;
	}

	public void setBirtdate(Date birtdate) {
		this.birtdate = birtdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Consultation> getConsultations() {
		return this.consultations;
	}

	public void setConsultations(List<Consultation> consultations) {
		this.consultations = consultations;
	}

	public Consultation addConsultation(Consultation consultation) {
		getConsultations().add(consultation);
		consultation.setPatient(this);

		return consultation;
	}

	public Consultation removeConsultation(Consultation consultation) {
		getConsultations().remove(consultation);
		consultation.setPatient(null);

		return consultation;
	}

	@Override
	public String toString() {
		return name+" ["+pnc+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pnc;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (pnc != other.pnc)
			return false;
		return true;
	}
	
	public String getMedicalHistory(){
		String history="";
		if(consultations!=null){
			for(Consultation c:consultations){
				history+="Observations : "+c.getObservations()+"\r\n";
				history+="Diagnosis    : "+c.getDiagnosis()+"\r\n";
				history+="Prescription : "+c.getPrescription()+"\r\n";
				history+="\r\n";
			}
		}
		
		return history;
	}

}