package com.magicdroidx.raknetty.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * raknetty Project
 * Author: MagicDroidX
 */
public class VarIntOutputStream extends DataOutputStream implements VarIntOutput {

    public VarIntOutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void writeVarInt(int value) throws IOException {
        writeUnsignedVarInt(VarIntOutput.encodeZigZag32(value));
    }

    @Override
    public void writeUnsignedVarInt(int value) throws IOException {
        while (true) {
            if ((value & ~0x7F) == 0) {
                writeByte(value);
                return;
            } else {
                writeByte((value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }

    @Override
    public void writeVarLong(long value) throws IOException {
        writeUnsignedVarLong(VarIntOutput.encodeZigZag64(value));
    }

    @Override
    public void writeUnsignedVarLong(long value) throws IOException {
        while (true) {
            if ((value & ~0x7FL) == 0) {
                writeByte((int) value);
                return;
            } else {
                writeByte(((int) value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }
}
