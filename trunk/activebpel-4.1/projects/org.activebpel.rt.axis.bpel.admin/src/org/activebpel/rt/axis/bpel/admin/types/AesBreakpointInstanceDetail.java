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

public class AesBreakpointInstanceDetail  implements java.io.Serializable {
    private javax.xml.namespace.QName processName;

    private java.lang.String nodePath;

    public AesBreakpointInstanceDetail() {
    }

    public AesBreakpointInstanceDetail(
           javax.xml.namespace.QName processName,
           java.lang.String nodePath) {
           this.processName = processName;
           this.nodePath = nodePath;
    }


    /**
     * Gets the processName value for this AesBreakpointInstanceDetail.
     * 
     * @return processName
     */
    public javax.xml.namespace.QName getProcessName() {
        return processName;
    }


    /**
     * Sets the processName value for this AesBreakpointInstanceDetail.
     * 
     * @param processName
     */
    public void setProcessName(javax.xml.namespace.QName processName) {
        this.processName = processName;
    }


    /**
     * Gets the nodePath value for this AesBreakpointInstanceDetail.
     * 
     * @return nodePath
     */
    public java.lang.String getNodePath() {
        return nodePath;
    }


    /**
     * Sets the nodePath value for this AesBreakpointInstanceDetail.
     * 
     * @param nodePath
     */
    public void setNodePath(java.lang.String nodePath) {
        this.nodePath = nodePath;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesBreakpointInstanceDetail)) return false;
        AesBreakpointInstanceDetail other = (AesBreakpointInstanceDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.processName==null && other.getProcessName()==null) || 
             (this.processName!=null &&
              this.processName.equals(other.getProcessName()))) &&
            ((this.nodePath==null && other.getNodePath()==null) || 
             (this.nodePath!=null &&
              this.nodePath.equals(other.getNodePath())));
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
        if (getProcessName() != null) {
            _hashCode += getProcessName().hashCode();
        }
        if (getNodePath() != null) {
            _hashCode += getNodePath().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
