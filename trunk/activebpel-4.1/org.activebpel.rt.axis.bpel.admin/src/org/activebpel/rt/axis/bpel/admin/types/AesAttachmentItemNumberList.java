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

public class AesAttachmentItemNumberList  implements java.io.Serializable {
    private java.lang.Integer[] itemNumber;

    public AesAttachmentItemNumberList() {
    }

    public AesAttachmentItemNumberList(
           java.lang.Integer[] itemNumber) {
           this.itemNumber = itemNumber;
    }


    /**
     * Gets the itemNumber value for this AesAttachmentItemNumberList.
     * 
     * @return itemNumber
     */
    public java.lang.Integer[] getItemNumber() {
        return itemNumber;
    }


    /**
     * Sets the itemNumber value for this AesAttachmentItemNumberList.
     * 
     * @param itemNumber
     */
    public void setItemNumber(java.lang.Integer[] itemNumber) {
        this.itemNumber = itemNumber;
    }

    public java.lang.Integer getItemNumber(int i) {
        return this.itemNumber[i];
    }

    public void setItemNumber(int i, java.lang.Integer _value) {
        this.itemNumber[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesAttachmentItemNumberList)) return false;
        AesAttachmentItemNumberList other = (AesAttachmentItemNumberList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.itemNumber==null && other.getItemNumber()==null) || 
             (this.itemNumber!=null &&
              java.util.Arrays.equals(this.itemNumber, other.getItemNumber())));
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
        if (getItemNumber() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItemNumber());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItemNumber(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
