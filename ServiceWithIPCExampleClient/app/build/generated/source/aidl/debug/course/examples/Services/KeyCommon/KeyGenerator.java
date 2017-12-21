/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\ram_n\\Desktop\\Subjets\\SDMA\\Example apps\\Folder - Services\\ServiceWithIPCExampleClient\\app\\src\\main\\aidl\\course\\examples\\Services\\KeyCommon\\KeyGenerator.aidl
 */
package course.examples.Services.KeyCommon;
public interface KeyGenerator extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements course.examples.Services.KeyCommon.KeyGenerator
{
private static final java.lang.String DESCRIPTOR = "course.examples.Services.KeyCommon.KeyGenerator";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an course.examples.Services.KeyCommon.KeyGenerator interface,
 * generating a proxy if needed.
 */
public static course.examples.Services.KeyCommon.KeyGenerator asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof course.examples.Services.KeyCommon.KeyGenerator))) {
return ((course.examples.Services.KeyCommon.KeyGenerator)iin);
}
return new course.examples.Services.KeyCommon.KeyGenerator.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getMonthlyCash:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getMonthlyCash(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getDailyCash:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
java.lang.String _result = this.getDailyCash(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getYearlyAverage:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getYearlyAverage(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_status:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.status(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements course.examples.Services.KeyCommon.KeyGenerator
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.lang.String getMonthlyCash(int year) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(year);
mRemote.transact(Stub.TRANSACTION_getMonthlyCash, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getDailyCash(int month, int day, int year, int days) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(month);
_data.writeInt(day);
_data.writeInt(year);
_data.writeInt(days);
mRemote.transact(Stub.TRANSACTION_getDailyCash, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getYearlyAverage(int year) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(year);
mRemote.transact(Stub.TRANSACTION_getYearlyAverage, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String status(java.lang.String x) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(x);
mRemote.transact(Stub.TRANSACTION_status, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getMonthlyCash = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getDailyCash = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getYearlyAverage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_status = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public java.lang.String getMonthlyCash(int year) throws android.os.RemoteException;
public java.lang.String getDailyCash(int month, int day, int year, int days) throws android.os.RemoteException;
public java.lang.String getYearlyAverage(int year) throws android.os.RemoteException;
public java.lang.String status(java.lang.String x) throws android.os.RemoteException;
}
