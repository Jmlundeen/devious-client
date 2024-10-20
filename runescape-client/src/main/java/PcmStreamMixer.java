import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bl")
@Implements("PcmStreamMixer")
public class PcmStreamMixer extends PcmStream {
	@ObfuscatedName("au")
	@ObfuscatedSignature(
		descriptor = "Low;"
	)
	@Export("subStreams")
	NodeDeque subStreams;
	@ObfuscatedName("ae")
	@ObfuscatedSignature(
		descriptor = "Low;"
	)
	NodeDeque field271;
	@ObfuscatedName("ao")
	int field273;
	@ObfuscatedName("at")
	int field272;

	public PcmStreamMixer() {
		this.subStreams = new NodeDeque();
		this.field271 = new NodeDeque();
		this.field273 = 0;
		this.field272 = -1;
	}

	@ObfuscatedName("au")
	@ObfuscatedSignature(
		descriptor = "(Lbw;)V"
	)
	@Export("addSubStream")
	public final synchronized void addSubStream(PcmStream var1) {
		this.subStreams.addLast(var1);
	}

	@ObfuscatedName("ae")
	@ObfuscatedSignature(
		descriptor = "(Lbw;)V"
	)
	@Export("removeSubStream")
	public final synchronized void removeSubStream(PcmStream var1) {
		var1.remove();
	}

	@ObfuscatedName("ao")
	void method714() {
		if (this.field273 > 0) {
			for (PcmStreamMixerListener var1 = (PcmStreamMixerListener)this.field271.last(); var1 != null; var1 = (PcmStreamMixerListener)this.field271.previous()) {
				var1.field420 -= this.field273;
			}

			this.field272 -= this.field273;
			this.field273 = 0;
		}

	}

	@ObfuscatedName("at")
	@ObfuscatedSignature(
		descriptor = "(Lsj;Lcs;)V"
	)
	void method715(Node var1, PcmStreamMixerListener var2) {
		while (this.field271.sentinel != var1 && ((PcmStreamMixerListener)var1).field420 <= var2.field420) {
			var1 = var1.previous;
		}

		NodeDeque.NodeDeque_addBefore(var2, var1);
		this.field272 = ((PcmStreamMixerListener)this.field271.sentinel.previous).field420;
	}

	@ObfuscatedName("ac")
	@ObfuscatedSignature(
		descriptor = "(Lcs;)V"
	)
	void method716(PcmStreamMixerListener var1) {
		var1.remove();
		var1.remove2();
		Node var2 = this.field271.sentinel.previous;
		if (var2 == this.field271.sentinel) {
			this.field272 = -1;
		} else {
			this.field272 = ((PcmStreamMixerListener)var2).field420;
		}

	}

	@ObfuscatedName("ai")
	@ObfuscatedSignature(
		descriptor = "()Lbw;"
	)
	@Export("firstSubStream")
	protected PcmStream firstSubStream() {
		return (PcmStream)this.subStreams.last();
	}

	@ObfuscatedName("az")
	@ObfuscatedSignature(
		descriptor = "()Lbw;"
	)
	@Export("nextSubStream")
	protected PcmStream nextSubStream() {
		return (PcmStream)this.subStreams.previous();
	}

	@ObfuscatedName("ap")
	protected int vmethod5973() {
		return 0;
	}

	@ObfuscatedName("aa")
	@Export("fill")
	public final synchronized void fill(int[] var1, int var2, int var3) {
		do {
			if (this.field272 < 0) {
				this.updateSubStreams(var1, var2, var3);
				return;
			}

			if (var3 + this.field273 < this.field272) {
				this.field273 += var3;
				this.updateSubStreams(var1, var2, var3);
				return;
			}

			int var4 = this.field272 - this.field273;
			this.updateSubStreams(var1, var2, var4);
			var2 += var4;
			var3 -= var4;
			this.field273 += var4;
			this.method714();
			PcmStreamMixerListener var5 = (PcmStreamMixerListener)this.field271.last();
			synchronized(var5) {
				int var7 = var5.update();
				if (var7 < 0) {
					var5.field420 = 0;
					this.method716(var5);
				} else {
					var5.field420 = var7;
					this.method715(var5.previous, var5);
				}
			}
		} while(var3 != 0);

	}

	@ObfuscatedName("af")
	@Export("updateSubStreams")
	void updateSubStreams(int[] var1, int var2, int var3) {
		for (PcmStream var4 = (PcmStream)this.subStreams.last(); var4 != null; var4 = (PcmStream)this.subStreams.previous()) {
			var4.update(var1, var2, var3);
		}

	}

	@ObfuscatedName("ad")
	@Export("skip")
	public final synchronized void skip(int var1) {
		do {
			if (this.field272 < 0) {
				this.skipSubStreams(var1);
				return;
			}

			if (this.field273 + var1 < this.field272) {
				this.field273 += var1;
				this.skipSubStreams(var1);
				return;
			}

			int var2 = this.field272 - this.field273;
			this.skipSubStreams(var2);
			var1 -= var2;
			this.field273 += var2;
			this.method714();
			PcmStreamMixerListener var3 = (PcmStreamMixerListener)this.field271.last();
			synchronized(var3) {
				int var5 = var3.update();
				if (var5 < 0) {
					var3.field420 = 0;
					this.method716(var3);
				} else {
					var3.field420 = var5;
					this.method715(var3.previous, var3);
				}
			}
		} while(var1 != 0);

	}

	@ObfuscatedName("aq")
	@Export("skipSubStreams")
	void skipSubStreams(int var1) {
		for (PcmStream var2 = (PcmStream)this.subStreams.last(); var2 != null; var2 = (PcmStream)this.subStreams.previous()) {
			var2.skip(var1);
		}

	}
}
