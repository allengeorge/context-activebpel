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
package org.activebpel.rt.axis.bpel.admin;

public interface IAeActiveBpelAdmin extends java.rmi.Remote {
    public org.activebpel.rt.axis.bpel.admin.types.AesConfigurationType getConfiguration(org.activebpel.rt.axis.bpel.admin.types.AesVoidType input) throws java.rmi.RemoteException, org.activebpel.rt.axis.bpel.admin.types.AdminFault;
    public void setConfiguration(org.activebpel.rt.axis.bpel.admin.types.AesConfigurationType input) throws java.rmi.RemoteException, org.activebpel.rt.axis.bpel.admin.types.AdminFault;
    public void suspendProcess(org.activebpel.rt.axis.bpel.admin.types.AesProcessType input) throws java.rmi.RemoteException, org.activebpel.rt.axis.bpel.admin.types.AdminFault;
    public void resumeProcess(org.activebpel.rt.axis.bpel.admin.types.AesProcessType input) throws java.rmi.RemoteException, org.activebpel.rt.axis.bpel.admin.types.AdminFault;
    public void resumeProcessObject(org.activebpel.rt.axis.bpel.admin.types.AesProcessObjectType input) throws java.rmi.RemoteException, org.activebpel.rt.axis.bpel.admin.types.AdminFault;
    public void terminateProcess(org.activebpel.rt.axis.bpel.admin.types.AesProcessType input) throws java.rmi.RemoteException, org.activebpel.rt.axis.bpel.admin.types.AdminFault;
    public void addEngineListener(org.activebpel.rt.axis.bpel.admin.types.AesEngineRequestType input) throws java.rmi.RemoteException;
    public void addBreakpointListener(org.activebpel.rt.axis.bpel.admin.types.AesBreakpointRequestType input) throws java.rmi.RemoteException;
    public void updateBreakpointList(org.activebpel.rt.axis.bpel.admin.types.AesBreakpointRequestType input) throws java.rmi.RemoteException;
    public void removeEngineListener(org.activebpel.rt.axis.bpel.admin.types.AesEngineRequestType input) throws java.rmi.RemoteException;
    public void removeBreakpointListener(org.activebpel.rt.axis.bpel.admin.types.AesRemoveBreakpointRequestType input) throws java.rmi.RemoteException;
    public void addProcessListener(org.activebpel.rt.axis.bpel.admin.types.AesProcessRequestType input) throws java.rmi.RemoteException;
    public void removeProcessListener(org.activebpel.rt.axis.bpel.admin.types.AesProcessRequestType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesStringResponseType getVariable(org.activebpel.rt.axis.bpel.admin.types.AesGetVariableDataType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesStringResponseType setVariable(org.activebpel.rt.axis.bpel.admin.types.AesSetVariableDataType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesAddAttachmentResponseType addAttachment(org.activebpel.rt.axis.bpel.admin.types.AesAddAttachmentDataType input, byte[] attachment) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesStringResponseType removeAttachments(org.activebpel.rt.axis.bpel.admin.types.AesRemoveAttachmentDataType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesProcessListType getProcessList(org.activebpel.rt.axis.bpel.admin.types.AesProcessFilterType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesProcessDetailType getProcessDetail(org.activebpel.rt.axis.bpel.admin.types.AesProcessType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesStringResponseType getProcessState(org.activebpel.rt.axis.bpel.admin.types.AesProcessType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesDigestType getProcessDigest(org.activebpel.rt.axis.bpel.admin.types.AesProcessType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesStringResponseType getProcessDef(org.activebpel.rt.axis.bpel.admin.types.AesProcessType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesStringResponseType getProcessLog(org.activebpel.rt.axis.bpel.admin.types.AesProcessType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesStringResponseType deployBpr(org.activebpel.rt.axis.bpel.admin.types.AesDeployBprType input) throws java.rmi.RemoteException;
    public void setPartnerLinkData(org.activebpel.rt.axis.bpel.admin.types.AesSetPartnerLinkType input) throws java.rmi.RemoteException;
    public void setCorrelationSetData(org.activebpel.rt.axis.bpel.admin.types.AesSetCorrelationType input) throws java.rmi.RemoteException;
    public void retryActivity(org.activebpel.rt.axis.bpel.admin.types.AesRetryActivityType input) throws java.rmi.RemoteException;
    public void completeActivity(org.activebpel.rt.axis.bpel.admin.types.AesCompleteActivityType input) throws java.rmi.RemoteException;
    public org.activebpel.rt.axis.bpel.admin.types.AesStringResponseType getAPIVersion(org.activebpel.rt.axis.bpel.admin.types.AesVoidType input) throws java.rmi.RemoteException;
}
