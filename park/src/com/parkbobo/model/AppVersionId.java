package com.parkbobo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class AppVersionId  implements java.io.Serializable {
	private static final long serialVersionUID = -7642138058957659666L;
	private Long versioncode;
    private Long channelid;

    public AppVersionId() {
    }
    public AppVersionId(Long versioncode, Long channelid) {
        this.versioncode = versioncode;
        this.channelid = channelid;
    }

    @Column(name="versioncode", nullable=false)
    public Long getVersioncode() {
        return this.versioncode;
    }
    
    public void setVersioncode(Long versioncode) {
        this.versioncode = versioncode;
    }

    @Column(name="channelid", nullable=false)

    public Long getChannelid() {
        return this.channelid;
    }
    
    public void setChannelid(Long channelid) {
        this.channelid = channelid;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AppVersionId) ) return false;
		 AppVersionId castOther = ( AppVersionId ) other; 
         
		 return ( (this.getVersioncode()==castOther.getVersioncode()) || ( this.getVersioncode()!=null && castOther.getVersioncode()!=null && this.getVersioncode().equals(castOther.getVersioncode()) ) )
 && ( (this.getChannelid()==castOther.getChannelid()) || ( this.getChannelid()!=null && castOther.getChannelid()!=null && this.getChannelid().equals(castOther.getChannelid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getVersioncode() == null ? 0 : this.getVersioncode().hashCode() );
         result = 37 * result + ( getChannelid() == null ? 0 : this.getChannelid().hashCode() );
         return result;
   }   
}