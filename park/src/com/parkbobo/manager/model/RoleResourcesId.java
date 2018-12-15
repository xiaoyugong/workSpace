package com.parkbobo.manager.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable

public class RoleResourcesId  implements java.io.Serializable {


	private static final long serialVersionUID = -3281583061772388798L;
	private Long roleId;
     private Long resourcesId;


    public RoleResourcesId() {
    }

    
    public RoleResourcesId(Long roleId, Long resourcesId) {
        this.roleId = roleId;
        this.resourcesId = resourcesId;
    }

   

    @Column(name="role_id", nullable=false)

    public Long getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name="resources_id", nullable=false)

    public Long getResourcesId() {
        return this.resourcesId;
    }
    
    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RoleResourcesId) ) return false;
		 RoleResourcesId castOther = ( RoleResourcesId ) other; 
         
		 return ( (this.getRoleId()==castOther.getRoleId()) || ( this.getRoleId()!=null && castOther.getRoleId()!=null && this.getRoleId().equals(castOther.getRoleId()) ) )
 && ( (this.getResourcesId()==castOther.getResourcesId()) || ( this.getResourcesId()!=null && castOther.getResourcesId()!=null && this.getResourcesId().equals(castOther.getResourcesId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoleId() == null ? 0 : this.getRoleId().hashCode() );
         result = 37 * result + ( getResourcesId() == null ? 0 : this.getResourcesId().hashCode() );
         return result;
   }   





}