                package me.lc.vodka.module.impl.other;


                import me.lc.vodka.Vodka;
                import me.lc.vodka.event.EventTarget;
                import me.lc.vodka.event.events.EventUpdate;
                import me.lc.vodka.helper.TimeHelper;
                import me.lc.vodka.module.Category;
                import me.lc.vodka.module.Module;
                import me.lc.vodka.setting.Setting;

                import java.util.ArrayList;
                import java.util.Random;


                public class Spammer extends Module {
                        public static ArrayList<String> text = new ArrayList<>();
                        TimeHelper delay = new TimeHelper();
                        public Setting spammerdelay;
                        public static Setting M;

                    Runnable r2 = ()-> System.out.println("233333333");

            public Spammer()
            {
                    super("Spammer",0, Category.OTHER);
                    Vodka.INSTANCE.SETTING_MANAGER.addSetting(spammerdelay = (new Setting(this,"Delay",1.0D,1.0D,5.0D,true)));
                    Vodka.INSTANCE.SETTING_MANAGER.addSetting(M = (new Setting(this,"Text","Vodka | Mady by Lightcolour",text)));
                    text.add(0,"Vodka | Mady by Lightcolour");
            }

        @EventTarget
        public void onOther(EventUpdate e) {
                 Random r = new Random();
            if (this.delay.isDelayComplete(((Double)this.spammerdelay.getCurrentValue()).longValue() * 1000L))
                {
                this.mc.thePlayer.sendChatMessage(M.getCurrentOption()+"[" + r.nextInt(346262) + "]");
                this.delay.reset();
                }
        }
}