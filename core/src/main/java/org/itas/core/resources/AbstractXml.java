package org.itas.core.resources;

import static org.itas.core.bytecode.Type.booleanType;
import static org.itas.core.bytecode.Type.byteType;
import static org.itas.core.bytecode.Type.charType;
import static org.itas.core.bytecode.Type.doubleType;
import static org.itas.core.bytecode.Type.enumByteType;
import static org.itas.core.bytecode.Type.enumIntType;
import static org.itas.core.bytecode.Type.enumStringType;
import static org.itas.core.bytecode.Type.enumType;
import static org.itas.core.bytecode.Type.floatType;
import static org.itas.core.bytecode.Type.intType;
import static org.itas.core.bytecode.Type.listType;
import static org.itas.core.bytecode.Type.longType;
import static org.itas.core.bytecode.Type.mapType;
import static org.itas.core.bytecode.Type.resourceType;
import static org.itas.core.bytecode.Type.setType;
import static org.itas.core.bytecode.Type.shortType;
import static org.itas.core.bytecode.Type.stringType;
import static org.itas.core.bytecode.Type.timeStampType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.itas.common.ItasException;

public abstract class AbstractXml {
	
	protected abstract String confiMessage(Field field);

	protected abstract String formatMessage(Field field, String value);
	
	protected void load(List<Field> fields, Attribute attribute) throws Exception {
		for (Field field : fields) {
			if (Modifier.isFinal(field.getModifiers()) || 
  				Modifier.isStatic(field.getModifiers())) {
  				continue;
			}
				
			fill(attribute, field);
		}
	}

	private void fill(Attribute attribute, Field field) throws Exception {
		try {
			if (!field.isAccessible()) {
			  field.setAccessible(true);
			}
			
			if (booleanType.isType(field.getType())) {
			  field.setBoolean(this, attribute.getBoolean(field));
			} else if (byteType.isType(field.getType())) {
			  field.setByte(this, attribute.getByte(field));
			} else if (charType.isType(field.getType())) {
			  field.setChar(this, attribute.getChar(field));
			} else if (shortType.isType(field.getType())) {
			  field.setShort(this, attribute.getShort(field));
			} else if (intType.isType(field.getType())) {
			  field.setInt(this, attribute.getInt(field));
			} else if (longType.isType(field.getType())) {
			  field.setLong(this, attribute.getLong(field));
			} else if (floatType.isType(field.getType())) {
			  field.setFloat(this, attribute.getFloat(field));
			} else if (doubleType.isType(field.getType())) {
			  field.setDouble(this, attribute.getDouble(field));
			} else if (stringType.isType(field.getType())) {
			  field.set(this, attribute.getString(field));
			} else if (resourceType.isType(field.getType())) {
			  field.set(this, attribute.getResource(field));
			} else if (enumByteType.isType(field.getType())) {
			  field.set(this, attribute.getEnumByte(field));
			} else if (enumIntType.isType(field.getType())) {
			  field.set(this, attribute.getEnumInt(field));
			} else if (enumStringType.isType(field.getType())) {
		      field.set(this, attribute.getEnumString(field));
			} else if (enumType.isType(field.getType())) {
		    field.set(this, attribute.getEnum(field));
			} else if (setType.isType(field.getType())) {
			  field.set(this, attribute.getSet(field));
			} else if (listType.isType(field.getType())) {
			  field.set(this, attribute.getList(field));
			} else if (mapType.isType(field.getType())) {
			  field.set(this, attribute.getMap(field));
			} else if (timeStampType.isType(field.getType())) {
			  field.set(this, attribute.getTimestamp(field));
			} else {
			  throw new ItasException("unSupported:[type:" + field.getType() + "]");
			}
		} catch (FieldNotConfigException e) {
			throw e.setMessage(confiMessage(field));
		} catch (NumberFormatException e) {
			throw new FieldConfigFormatException(
					formatMessage(field, attribute.getValue(field.getName())));
		}
	}
  
}
