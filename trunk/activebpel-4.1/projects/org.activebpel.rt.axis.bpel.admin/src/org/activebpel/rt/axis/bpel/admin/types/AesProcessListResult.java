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


/**
 * The "empty" element is a side affect of the JavaBean which contains
 * a read only property that indicates if the result is empty.
 */
public class AesProcessListResult  implements java.io.Serializable {
    private int totalRowCount;

    private boolean completeRowCount;

    private org.activebpel.rt.axis.bpel.admin.types.AesProcessListResultRowDetails rowDetails;

    private boolean empty;

    public AesProcessListResult() {
    }

    public AesProcessListResult(
           int totalRowCount,
           boolean completeRowCount,
           org.activebpel.rt.axis.bpel.admin.types.AesProcessListResultRowDetails rowDetails,
           boolean empty) {
           this.totalRowCount = totalRowCount;
           this.completeRowCount = completeRowCount;
           this.rowDetails = rowDetails;
           this.empty = empty;
    }


    /**
     * Gets the totalRowCount value for this AesProcessListResult.
     * 
     * @return totalRowCount
     */
    public int getTotalRowCount() {
        return totalRowCount;
    }


    /**
     * Sets the totalRowCount value for this AesProcessListResult.
     * 
     * @param totalRowCount
     */
    public void setTotalRowCount(int totalRowCount) {
        this.totalRowCount = totalRowCount;
    }


    /**
     * Gets the completeRowCount value for this AesProcessListResult.
     * 
     * @return completeRowCount
     */
    public boolean isCompleteRowCount() {
        return completeRowCount;
    }


    /**
     * Sets the completeRowCount value for this AesProcessListResult.
     * 
     * @param completeRowCount
     */
    public void setCompleteRowCount(boolean completeRowCount) {
        this.completeRowCount = completeRowCount;
    }


    /**
     * Gets the rowDetails value for this AesProcessListResult.
     * 
     * @return rowDetails
     */
    public org.activebpel.rt.axis.bpel.admin.types.AesProcessListResultRowDetails getRowDetails() {
        return rowDetails;
    }


    /**
     * Sets the rowDetails value for this AesProcessListResult.
     * 
     * @param rowDetails
     */
    public void setRowDetails(org.activebpel.rt.axis.bpel.admin.types.AesProcessListResultRowDetails rowDetails) {
        this.rowDetails = rowDetails;
    }


    /**
     * Gets the empty value for this AesProcessListResult.
     * 
     * @return empty
     */
    public boolean isEmpty() {
        return empty;
    }


    /**
     * Sets the empty value for this AesProcessListResult.
     * 
     * @param empty
     */
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesProcessListResult)) return false;
        AesProcessListResult other = (AesProcessListResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.totalRowCount == other.getTotalRowCount() &&
            this.completeRowCount == other.isCompleteRowCount() &&
            ((this.rowDetails==null && other.getRowDetails()==null) || 
             (this.rowDetails!=null &&
              this.rowDetails.equals(other.getRowDetails()))) &&
            this.empty == other.isEmpty();
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
        _hashCode += getTotalRowCount();
        _hashCode += (isCompleteRowCount() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getRowDetails() != null) {
            _hashCode += getRowDetails().hashCode();
        }
        _hashCode += (isEmpty() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

}
