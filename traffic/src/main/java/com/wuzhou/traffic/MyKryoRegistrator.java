package com.wuzhou.traffic;

import org.apache.spark.serializer.KryoRegistrator;

import com.esotericsoftware.kryo.Kryo;
import com.kmlc.protobuf.OffenceSnapDataProtos;

public class MyKryoRegistrator implements KryoRegistrator {

	public void registerClasses(Kryo kryo) {
		// TODO Auto-generated method stub
		kryo.register(OffenceSnapDataProtos.class);
	}
}


