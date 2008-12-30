/*
 * Copyright (c) 2004-2006 Active Endpoints, Inc.
 *
 * This program is licensed under the terms of the GNU General Public License
 * Version 2 (the "License") as published by the Free Software Foundation, and 
 * the ActiveBPEL Licensing Policies (the "Policies").  A copy of the License 
 * and the Policies were distributed with this program.  
 *
 * The License is available at:
 * http: *www.gnu.org/copyleft/gpl.html
 *
 * The Policies are available at:
 * http: *www.activebpel.org/licensing/index.html
 *
 * Unless required by applicable law or agreed to in writing, this program is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied.  See the License and the Policies
 * for specific language governing the use of this program.
 */
package org.activebpel.rt.axis.bpel.admin.types;

public class AesSetPartnerLinkType  implements java.io.Serializable {
    private long pid;

    private boolean partnerRole;

    private java.lang.String locationPath;

    private java.lang.String data;

    public AesSetPartnerLinkType() {
    }

    public AesSetPartnerLinkType(
           long pid,
           boolean partnerRole,
           java.lang.String locationPath,
           java.lang.String data) {
           this.pid = pid;
           this.partnerRole = partnerRole;
           this.locationPath = locationPath;
           this.data = data;
    }


    /**
     * Gets the pid value for this AesSetPartnerLinkType.
     * 
     * @return pid
     */
    public long getPid() {
        return pid;
    }


    /**
     * Sets the pid value for this AesSetPartnerLinkType.
     * 
     * @param pid
     */
    public void setPid(long pid) {
        this.pid = pid;
    }


    /**
     * Gets the partnerRole value for this AesSetPartnerLinkType.
     * 
     * @return partnerRole
     */
    public boolean isPartnerRole() {
        return partnerRole;
    }


    /**
     * Sets the partnerRole value for this AesSetPartnerLinkType.
     * 
     * @param partnerRole
     */
    public void setPartnerRole(boolean partnerRole) {
        this.partnerRole = partnerRole;
    }


    /**
     * Gets the locationPath value for this AesSetPartnerLinkType.
     * 
     * @return locationPath
     */
    public java.lang.String getLocationPath() {
        return locationPath;
    }


    /**
     * Sets the locationPath value for this AesSetPartnerLinkType.
     * 
     * @param locationPath
     */
    public void setLocationPath(java.lang.String locationPath) {
        this.locationPath = locationPath;
    }


    /**
     * Gets the data value for this AesSetPartnerLinkType.
     * 
     * @return data
     */
    public java.lang.String getData() {
        return data;
    }


    /**
     * Sets the data value for this AesSetPartnerLinkType.
     * 
     * @param data
     */
    public void setData(java.lang.String data) {
        this.data = data;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesSetPartnerLinkType)) return false;
        AesSetPartnerLinkType other = (AesSetPartnerLinkType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.pid == other.getPid() &&
            this.partnerRole == other.isPartnerRole() &&
            ((this.locationPath==null && other.getLocationPath()==null) || 
             (this.locationPath!=null &&
              this.locationPath.equals(other.getLocationPath()))) &&
            ((this.data==null && other.getData()==null) || 
             (this.data!=null &&
              this.data.equals(other.getData())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += new Long(getPid()).hashCode();
        _hashCode += (isPartnerRole() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getLocationPath() != null) {
            _hashCode += getLocationPath().hashCode();
        }
        if (getData() != null) {
            _hashCode += getData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
