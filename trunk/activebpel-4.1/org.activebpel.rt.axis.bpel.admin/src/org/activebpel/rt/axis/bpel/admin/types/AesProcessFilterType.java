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

public class AesProcessFilterType  implements java.io.Serializable {
    private org.activebpel.rt.axis.bpel.admin.types.AesProcessFilter filter;

    public AesProcessFilterType() {
    }

    public AesProcessFilterType(
           org.activebpel.rt.axis.bpel.admin.types.AesProcessFilter filter) {
           this.filter = filter;
    }


    /**
     * Gets the filter value for this AesProcessFilterType.
     * 
     * @return filter
     */
    public org.activebpel.rt.axis.bpel.admin.types.AesProcessFilter getFilter() {
        return filter;
    }


    /**
     * Sets the filter value for this AesProcessFilterType.
     * 
     * @param filter
     */
    public void setFilter(org.activebpel.rt.axis.bpel.admin.types.AesProcessFilter filter) {
        this.filter = filter;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesProcessFilterType)) return false;
        AesProcessFilterType other = (AesProcessFilterType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.filter==null && other.getFilter()==null) || 
             (this.filter!=null &&
              this.filter.equals(other.getFilter())));
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
        if (getFilter() != null) {
            _hashCode += getFilter().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
