package apexutils

import java.io.ByteArrayInputStream
import org.freehep.util.io.ASCII85InputStream
import org.freehep.util.io.ASCII85OutputStream
import java.io.ByteArrayOutputStream
import scala.io.Source

object Ascii85Coder {
  val SUFFIX = "~>"

  def decodeAscii85StringToBytes(ascii85: String) = {
    val in = if (!ascii85.endsWith(SUFFIX)) ascii85 + SUFFIX else ascii85
    val in_byte = new ByteArrayInputStream(in.getBytes("ascii"))
    val in_ascii = new ASCII85InputStream(in_byte)
    Stream.continually(in_ascii.read).takeWhile(-1 !=).map(_.toByte).toArray
  }

  def encodeBytesToAscii85(bytes: Array[Byte]) = {
    val out_byte = new ByteArrayOutputStream
    val out_ascii = new ASCII85OutputStream(out_byte)
    out_ascii.write(bytes)
    out_ascii.finish()
    val out = out_byte.toString("ascii")
    if (out.endsWith(SUFFIX)) out.dropRight(2) else out
  }
}