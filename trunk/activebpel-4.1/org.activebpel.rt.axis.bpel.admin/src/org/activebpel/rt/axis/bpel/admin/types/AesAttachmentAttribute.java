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

public class AesAttachmentAttribute  implements java.io.Serializable {
    private java.lang.String attributeName;

    private java.lang.String attributeValue;

    public AesAttachmentAttribute() {
    }

    public AesAttachmentAttribute(
           java.lang.String attributeName,
           java.lang.String attributeValue) {
           this.attributeName = attributeName;
           this.attributeValue = attributeValue;
    }


    /**
     * Gets the attributeName value for this AesAttachmentAttribute.
     * 
     * @return attributeName
     */
    public java.lang.String getAttributeName() {
        return attributeName;
    }


    /**
     * Sets the attributeName value for this AesAttachmentAttribute.
     * 
     * @param attributeName
     */
    public void setAttributeName(java.lang.String attributeName) {
        this.attributeName = attributeName;
    }


    /**
     * Gets the attributeValue value for this AesAttachmentAttribute.
     * 
     * @return attributeValue
     */
    public java.lang.String getAttributeValue() {
        return attributeValue;
    }


    /**
     * Sets the attributeValue value for this AesAttachmentAttribute.
     * 
     * @param attributeValue
     */
    public void setAttributeValue(java.lang.String attributeValue) {
        this.attributeValue = attributeValue;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesAttachmentAttribute)) return false;
        AesAttachmentAttribute other = (AesAttachmentAttribute) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attributeName==null && other.getAttributeName()==null) || 
             (this.attributeName!=null &&
              this.attributeName.equals(other.getAttributeName()))) &&
            ((this.attributeValue==null && other.getAttributeValue()==null) || 
             (this.attributeValue!=null &&
              this.attributeValue.equals(other.getAttributeValue())));
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
        if (getAttributeName() != null) {
            _hashCode += getAttributeName().hashCode();
        }
        if (getAttributeValue() != null) {
            _hashCode += getAttributeValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
