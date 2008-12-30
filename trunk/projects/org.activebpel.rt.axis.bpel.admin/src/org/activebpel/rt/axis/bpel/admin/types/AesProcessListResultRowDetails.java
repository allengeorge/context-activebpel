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

public class AesProcessListResultRowDetails  implements java.io.Serializable {
    private org.activebpel.rt.axis.bpel.admin.types.AesProcessInstanceDetail[] item;

    public AesProcessListResultRowDetails() {
    }

    public AesProcessListResultRowDetails(
           org.activebpel.rt.axis.bpel.admin.types.AesProcessInstanceDetail[] item) {
           this.item = item;
    }


    /**
     * Gets the item value for this AesProcessListResultRowDetails.
     * 
     * @return item
     */
    public org.activebpel.rt.axis.bpel.admin.types.AesProcessInstanceDetail[] getItem() {
        return item;
    }


    /**
     * Sets the item value for this AesProcessListResultRowDetails.
     * 
     * @param item
     */
    public void setItem(org.activebpel.rt.axis.bpel.admin.types.AesProcessInstanceDetail[] item) {
        this.item = item;
    }

    public org.activebpel.rt.axis.bpel.admin.types.AesProcessInstanceDetail getItem(int i) {
        return this.item[i];
    }

    public void setItem(int i, org.activebpel.rt.axis.bpel.admin.types.AesProcessInstanceDetail _value) {
        this.item[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesProcessListResultRowDetails)) return false;
        AesProcessListResultRowDetails other = (AesProcessListResultRowDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.item==null && other.getItem()==null) || 
             (this.item!=null &&
              java.util.Arrays.equals(this.item, other.getItem())));
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
        if (getItem() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItem());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItem(), i);
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
