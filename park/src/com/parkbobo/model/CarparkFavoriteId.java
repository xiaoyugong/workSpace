package com.parkbobo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable

public class CarparkFavoriteId  implements java.io.Serializable {


	private static final long serialVersionUID = -8306652600392145781L;
	private String userid;
    private Long carparkid;



    public CarparkFavoriteId() {
    }

    
    public CarparkFavoriteId(String userid, Long carparkid) {
        this.userid = userid;
        this.carparkid = carparkid;
    }

   

    @Column(name="userid", nullable=false, length=100)

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name="carparkid", nullable=false)

    public Long getCarparkid() {
        return this.carparkid;
    }
    
    public void setCarparkid(Long carparkid) {
        this.carparkid = carparkid;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CarparkFavoriteId) ) return false;
		 CarparkFavoriteId castOther = ( CarparkFavoriteId ) other; 
         
		 return ( (this.getUserid()==castOther.getUserid()) || ( this.getUserid()!=null && castOther.getUserid()!=null && this.getUserid().equals(castOther.getUserid()) ) )
 && ( (this.getCarparkid()==castOther.getCarparkid()) || ( this.getCarparkid()!=null && castOther.getCarparkid()!=null && this.getCarparkid().equals(castOther.getCarparkid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserid() == null ? 0 : this.getUserid().hashCode() );
         result = 37 * result + ( getCarparkid() == null ? 0 : this.getCarparkid().hashCode() );
         return result;
   }   





}