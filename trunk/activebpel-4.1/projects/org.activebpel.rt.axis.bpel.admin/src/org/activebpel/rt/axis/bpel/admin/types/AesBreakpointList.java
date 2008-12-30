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

public class AesBreakpointList  implements java.io.Serializable {
    private org.activebpel.rt.axis.bpel.admin.types.AesBreakpointListRowDetails rowDetails;

    private int totalRowCount;

    public AesBreakpointList() {
    }

    public AesBreakpointList(
           org.activebpel.rt.axis.bpel.admin.types.AesBreakpointListRowDetails rowDetails,
           int totalRowCount) {
           this.rowDetails = rowDetails;
           this.totalRowCount = totalRowCount;
    }


    /**
     * Gets the rowDetails value for this AesBreakpointList.
     * 
     * @return rowDetails
     */
    public org.activebpel.rt.axis.bpel.admin.types.AesBreakpointListRowDetails getRowDetails() {
        return rowDetails;
    }


    /**
     * Sets the rowDetails value for this AesBreakpointList.
     * 
     * @param rowDetails
     */
    public void setRowDetails(org.activebpel.rt.axis.bpel.admin.types.AesBreakpointListRowDetails rowDetails) {
        this.rowDetails = rowDetails;
    }


    /**
     * Gets the totalRowCount value for this AesBreakpointList.
     * 
     * @return totalRowCount
     */
    public int getTotalRowCount() {
        return totalRowCount;
    }


    /**
     * Sets the totalRowCount value for this AesBreakpointList.
     * 
     * @param totalRowCount
     */
    public void setTotalRowCount(int totalRowCount) {
        this.totalRowCount = totalRowCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesBreakpointList)) return false;
        AesBreakpointList other = (AesBreakpointList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rowDetails==null && other.getRowDetails()==null) || 
             (this.rowDetails!=null &&
              this.rowDetails.equals(other.getRowDetails()))) &&
            this.totalRowCount == other.getTotalRowCount();
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
        if (getRowDetails() != null) {
            _hashCode += getRowDetails().hashCode();
        }
        _hashCode += getTotalRowCount();
        __hashCodeCalc = false;
        return _hashCode;
    }

}
