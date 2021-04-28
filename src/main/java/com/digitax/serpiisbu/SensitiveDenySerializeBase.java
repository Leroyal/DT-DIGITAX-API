package com.digitax.serpiisbu;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;
@XmlTransient
@SensitiveDenySerialize
public class SensitiveDenySerializeBase implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@SensitiveDenySerialize
	  private void writeObject(ObjectOutputStream out) throws IOException {
	    throw new NotSerializableException();
	  }
	@SensitiveDenySerialize
	  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	    throw new NotSerializableException();
	  }

	  /**
	   * Throw {@code NotSerializableException} to prevent handling case where no serialized 
	   * object was read.
	   * 
	   * @throws ObjectStreamException
	   *   if an error occurs (such as {@code NotSerializableException}).
	   */
	  @SensitiveDenySerialize
	  private void readObjectNoData() throws ObjectStreamException {
	    throw new NotSerializableException();
	  }
}
