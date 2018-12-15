package com.parkbobo.manager.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable

public class ManagerRoleId  implements java.io.Serializable {

	private static final long serialVersionUID = 4912202546400986755L;
	private Long userId;
     private Long roleId;


    public ManagerRoleId() {
    }

    
    public ManagerRoleId(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

   

    @Column(name="user_id", nullable=false)

    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name="role_id", nullable=false)

    public Long getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ManagerRoleId) ) return false;
		 ManagerRoleId castOther = ( ManagerRoleId ) other; 
         
		 return ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getRoleId()==castOther.getRoleId()) || ( this.getRoleId()!=null && castOther.getRoleId()!=null && this.getRoleId().equals(castOther.getRoleId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getRoleId() == null ? 0 : this.getRoleId().hashCode() );
         return result;
   }   





}