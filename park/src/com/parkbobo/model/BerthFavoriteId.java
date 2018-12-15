package com.parkbobo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable

public class BerthFavoriteId  implements java.io.Serializable {


	private static final long serialVersionUID = -4522899997675146782L;
	private Long gid;
    private String userid;


    public BerthFavoriteId() {
    }

    
    public BerthFavoriteId(Long gid, String userid) {
        this.gid = gid;
        this.userid = userid;
    }

   

    @Column(name="gid", nullable=false)

    public Long getGid() {
        return this.gid;
    }
    
    public void setGid(Long gid) {
        this.gid = gid;
    }

    @Column(name="userid", nullable=false, length=100)

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BerthFavoriteId) ) return false;
		 BerthFavoriteId castOther = ( BerthFavoriteId ) other; 
         
		 return ( (this.getGid()==castOther.getGid()) || ( this.getGid()!=null && castOther.getGid()!=null && this.getGid().equals(castOther.getGid()) ) )
 && ( (this.getUserid()==castOther.getUserid()) || ( this.getUserid()!=null && castOther.getUserid()!=null && this.getUserid().equals(castOther.getUserid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGid() == null ? 0 : this.getGid().hashCode() );
         result = 37 * result + ( getUserid() == null ? 0 : this.getUserid().hashCode() );
         return result;
   }   





}