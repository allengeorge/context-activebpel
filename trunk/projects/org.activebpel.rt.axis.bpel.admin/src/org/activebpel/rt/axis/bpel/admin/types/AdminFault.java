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
public class AdminFault extends java.rmi.RemoteException  implements java.io.Serializable {
    private java.lang.String info;

    private java.lang.String details;

    public AdminFault() {
    }

    public AdminFault(
           java.lang.String info,
           java.lang.String details) {
        this.info = info;
        this.details = details;
    }


    /**
     * Gets the info value for this AdminFault.
     * 
     * @return info
     */
    public java.lang.String getInfo() {
        return info;
    }


    /**
     * Sets the info value for this AdminFault.
     * 
     * @param info
     */
    public void setInfo(java.lang.String info) {
        this.info = info;
    }


    /**
     * Gets the details value for this AdminFault.
     * 
     * @return details
     */
    public java.lang.String getDetails() {
        return details;
    }


    /**
     * Sets the details value for this AdminFault.
     * 
     * @param details
     */
    public void setDetails(java.lang.String details) {
        this.details = details;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AdminFault)) return false;
        AdminFault other = (AdminFault) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.info==null && other.getInfo()==null) || 
             (this.info!=null &&
              this.info.equals(other.getInfo()))) &&
            ((this.details==null && other.getDetails()==null) || 
             (this.details!=null &&
              this.details.equals(other.getDetails())));
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
        if (getInfo() != null) {
            _hashCode += getInfo().hashCode();
        }
        if (getDetails() != null) {
            _hashCode += getDetails().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }
}
