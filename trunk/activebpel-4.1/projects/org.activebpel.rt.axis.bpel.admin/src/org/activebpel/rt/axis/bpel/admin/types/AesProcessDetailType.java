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

public class AesProcessDetailType  implements java.io.Serializable {
    private org.activebpel.rt.axis.bpel.admin.types.AesProcessInstanceDetail response;

    public AesProcessDetailType() {
    }

    public AesProcessDetailType(
           org.activebpel.rt.axis.bpel.admin.types.AesProcessInstanceDetail response) {
           this.response = response;
    }


    /**
     * Gets the response value for this AesProcessDetailType.
     * 
     * @return response
     */
    public org.activebpel.rt.axis.bpel.admin.types.AesProcessInstanceDetail getResponse() {
        return response;
    }


    /**
     * Sets the response value for this AesProcessDetailType.
     * 
     * @param response
     */
    public void setResponse(org.activebpel.rt.axis.bpel.admin.types.AesProcessInstanceDetail response) {
        this.response = response;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesProcessDetailType)) return false;
        AesProcessDetailType other = (AesProcessDetailType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.response==null && other.getResponse()==null) || 
             (this.response!=null &&
              this.response.equals(other.getResponse())));
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
        if (getResponse() != null) {
            _hashCode += getResponse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
