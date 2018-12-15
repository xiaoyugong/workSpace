package com.parkbobo.manager.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable

public class ManagerResourcesId  implements java.io.Serializable {



     /**
	 * 
	 */
	private static final long serialVersionUID = -281474657855673347L;
	private Long resourcesId;
     private Long userId;



    public ManagerResourcesId() {
    }

    
    public ManagerResourcesId(Long resourcesId, Long userId) {
        this.resourcesId = resourcesId;
        this.userId = userId;
    }

   

    @Column(name="resources_id", nullable=false)

    public Long getResourcesId() {
        return this.resourcesId;
    }
    
    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    @Column(name="user_id", nullable=false)

    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ManagerResourcesId) ) return false;
		 ManagerResourcesId castOther = ( ManagerResourcesId ) other; 
         
		 return ( (this.getResourcesId()==castOther.getResourcesId()) || ( this.getResourcesId()!=null && castOther.getResourcesId()!=null && this.getResourcesId().equals(castOther.getResourcesId()) ) )
 && ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getResourcesId() == null ? 0 : this.getResourcesId().hashCode() );
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         return result;
   }   





}