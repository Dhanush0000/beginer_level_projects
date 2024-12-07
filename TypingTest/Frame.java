import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener, KeyListener
{
    String passage="";
    String typedPass="";
    String message="";

    int typed=0;
    int count=0;
    int WPM;

    double start;
    double end;
    double elapsed;
    double seconds;

    boolean running;
    boolean ended;

    final int SCREEN_WIDTH;
    final int SCREEN_HEIGHT;
    final int DELAY=100;

    JButton button;
    Timer timer;
    JLabel label;

    public Frame()
    {
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN_WIDTH=720;
        SCREEN_HEIGHT=400;
        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        button=new JButton("Start");
        button.setFont(new Font("MV Boli",Font.BOLD,30));
        button.setForeground(Color.BLUE);
        button.setVisible(true);
        button.addActionListener(this);
        button.setFocusable(false);

        label=new JLabel();
        label.setText("Click the Start Button");
        label.setFont(new Font("MV Boli",Font.BOLD,30));
        label.setVisible(true);
        label.setOpaque(true);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.lightGray);

        this.add(button, BorderLayout.SOUTH);
        this.add(label, BorderLayout.NORTH);
        this.getContentPane().setBackground(Color.WHITE);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setResizable(false);
        this.setTitle("Typing Test");
        this.revalidate();
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        g.setFont(new Font("MV Boli", Font.BOLD, 25));

        if(running)
        {
            if(passage.length()>1)
            {
                g.drawString(passage.substring(0,50), g.getFont().getSize(), g.getFont().getSize()*5);
                g.drawString(passage.substring(50,100), g.getFont().getSize(), g.getFont().getSize()*7);
                g.drawString(passage.substring(100,150), g.getFont().getSize(), g.getFont().getSize()*9);
                g.drawString(passage.substring(150,200), g.getFont().getSize(), g.getFont().getSize()*11);
            }

            g.setColor(Color.BLUE);
            if(typedPass.length()>0)
            {
                if(typed<50)
                    g.drawString(typedPass.substring(0,typed), g.getFont().getSize(),g.getFont().getSize()*5);
                else
                    g.drawString(typedPass.substring(0,50), g.getFont().getSize(),g.getFont().getSize()*5);
            }
            if(typedPass.length()>50)
            {
                if(typed<100)
                    g.drawString(typedPass.substring(50,typed), g.getFont().getSize(),g.getFont().getSize()*7);
                else
                    g.drawString(typedPass.substring(50,100), g.getFont().getSize(),g.getFont().getSize()*7);
            }
            if(typedPass.length()>100)
            {
                if(typed<150)
                    g.drawString(typedPass.substring(100,typed), g.getFont().getSize(),g.getFont().getSize()*9);
                else
                    g.drawString(typedPass.substring(100,150), g.getFont().getSize(),g.getFont().getSize()*9);
            }
            if(typedPass.length()>150)
                g.drawString(typedPass.substring(150,typed), g.getFont().getSize(),g.getFont().getSize()*11);
            running=false;
        }
        if(ended)
        {
            if(WPM<=40)
                message="You are an Average Typist";
            else if(WPM>40 && WPM<=60)
                message="You are a Good Typist";
            else if(WPM>60 && WPM<=100)
                message="You are an Excellent Typist";
            else
                message="You are an Elite Typist";

            FontMetrics metrics=getFontMetrics(g.getFont());
            g.setColor(Color.BLUE);
            g.drawString("Typing Test Completed!", (SCREEN_WIDTH-metrics.stringWidth("Typing Test Completed!"))/2, g.getFont().getSize()*6);

            g.setColor(Color.BLACK);
            g.drawString("Typing Speed: "+WPM+" Words Per Minute", (SCREEN_WIDTH-metrics.stringWidth("Typing Speed: "+WPM+" Words Per Minute"))/2, g.getFont().getSize()*9);
            g.drawString(message, (SCREEN_WIDTH-metrics.stringWidth(message))/2, g.getFont().getSize()*11);

            timer.stop();
            ended=false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        if(passage.length()>1)
        {
            if(count==0)
                start=LocalTime.now().toNanoOfDay();
            else if(count==200)
            {
                end=LocalTime.now().toNanoOfDay();
                elapsed=end-start;
                seconds=elapsed/1000000000.0;
                WPM=(int)(((200.0/5)/seconds)*60);
                ended=true;
                running=false;
                count++;
            }
            char[] pass=passage.toCharArray();
            if(typed<200)
            {
                running=true;
                if(e.getKeyChar()==pass[typed])
                {
                    typedPass=typedPass+pass[typed];
                    typed++;
                    count++;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==button)
        {
            passage=getPassage();
            timer=new Timer(DELAY,this);
            timer.start();
            running=true;
            ended=false;

            typedPass="";
            message="";

            typed=0;
            count=0;
        }
        if(running)
            repaint();
        if(ended)
            repaint();
    }
    public static String getPassage()
    {
        ArrayList<String> Passages=new ArrayList<String>();
        String pas1="Many touch typists also use keyboard shortcuts or hotkeys when typing on a computer. This allows them to edit their document without having to take their hands off the keyboard to use a mouse. An example of a keyboard shortcut is pressing the Ctrl key plus the S key to save a";
        String pas2="A virtual assistant (typically abbreviated to VA) is generally self-employed and provides professional administrative, technical, or creative assistance to clients remotely from a home office. Many touch typists also use keyboard shortcuts or hotkeys when typing on a computer";
        String pas3="Frank Edward McGurrin, a court stenographer from Salt Lake City, Utah who taught typing classes, reportedly invented touch typing in 1888. On a standard keyboard for English speakers the home row keys are: \"ASDF\" for the left hand and \"JKL;\" for the right hand. The keyboar";
        String pas4="Income before securities transactions was up 10.8 percent from $13.49 million in 1982 to $14.95 million in 1983. Earnings per share (adjusted for a 10.5 percent stock dividend distributed on August 26) advanced 10 percent to $2.39 in 1983 from $2.17 in 1982. Earnings may rise ";
        String pas5="Historically, the fundamental role of pharmacists as a healthcare practitioner was to check and distribute drugs to doctors for medication that had been prescribed to patients. In more modern times, pharmacists advise patients and health care providers on the selection, dosage";
        String pas6="Proofreader applicants are tested primarily on their spelling, speed, and skill in finding errors in the sample text. Toward that end, they may be given a list of ten or twenty classically difficult words and a proofreading test, both tightly timed. The proofreading test will o";
        String pas7="In one study of average computer users, the average rate for transcription was 33 words per minute, and 19 words per minute for composition. In the same study, when the group was divided into \"fast\", \"moderate\" and \"slow\" groups, the average speeds were 40 wpm, 35 wpm, an";
        String pas8="A teacher's professional duties may extend beyond formal teaching. Outside of the classroom teachers may accompany students on field trips, supervise study halls, help with the organization of school functions, and serve as supervisors for extracurricular activities. In some e";
        String pas9="Web designers are expected to have an awareness of usability and if their role involves creating mark up then they are also expected to be up to date with web accessibility guidelines. The different areas of web design include web graphic design; interface design; authoring, i";
        String pas10="A data entry clerk is a member of staff employed to enter or update data into a computer system. Data is often entered into a computer from paper documents using a keyboard. The keyboards used can often have special keys and multiple colors to help in the task and speed up th";
        String pas11="The internet is a global network that connects millions of private, public, academic, business, and government networks. It is a network of networks that use the Internet Protocol Suite (TCP/IP) to link devices worldwide. The internet has enabled the development of a wide";
        String pas12="Artificial intelligence (AI) is the simulation of human intelligence processes by machines, particularly computer systems. These processes include learning (the acquisition of information and rules for using it), reasoning (using rules to reach approximate or definite";
        String pas13="The human brain is a complex organ responsible for controlling a wide range of functions, from basic survival instincts to higher cognitive processes such as reasoning, problem-solving, and memory. It consists of billions of neurons that communicate through electrical ";
        String pas14="In the field of renewable energy, solar power has emerged as one of the most widely used and promising sources of clean energy. By harnessing the power of the sun, solar panels can convert sunlight into electricity, reducing dependence on fossil fuels and lowering carbon ";
        String pas15="The concept of time travel has fascinated scientists, writers, and thinkers for centuries. While time travel remains a theoretical concept in the realm of physics, it has been a central theme in literature and popular culture. The idea of moving through time, ";
        String pas16="Space exploration has led to some of the most significant achievements in human history. From landing on the moon to sending rovers to Mars, space missions have expanded our understanding of the universe. The development of advanced technologies and the pursuit of knowledge";
        String pas17="Global warming refers to the long-term increase in Earth's average surface temperature due to human activities, primarily the burning of fossil fuels. This leads to the release of greenhouse gases like carbon dioxide, which trap heat in the atmosphere. The effects of global warming ";
        String pas18="The process of photosynthesis is essential for life on Earth, as it is the method by which plants convert sunlight into chemical energy. During photosynthesis, plants absorb sunlight, carbon dioxide, and water, and use them to produce glucose and oxygen. This process forms the basis";
        String pas19="In recent years, electric vehicles (EVs) have become increasingly popular as an alternative to traditional gasoline-powered cars. EVs are powered by electricity stored in rechargeable batteries, which makes them more environmentally friendly by reducing greenhouse gas emissions.";
        String pas20="Cryptocurrency is a type of digital or virtual currency that uses cryptography for security. Unlike traditional currencies issued by governments, cryptocurrencies are decentralized and operate on a technology called blockchain. Bitcoin, Ethereum, and other cryptocurrencies";


        Passages.add(pas1);
        Passages.add(pas2);
        Passages.add(pas3);
        Passages.add(pas4);
        Passages.add(pas5);
        Passages.add(pas6);
        Passages.add(pas7);
        Passages.add(pas8);
        Passages.add(pas9);
        Passages.add(pas10);
        Passages.add(pas11);
        Passages.add(pas12);
        Passages.add(pas13);
        Passages.add(pas14);
        Passages.add(pas15);
        Passages.add(pas16);
        Passages.add(pas17);
        Passages.add(pas18);
        Passages.add(pas19);
        Passages.add(pas20);

        Random rand=new Random();
        int place=(rand.nextInt(20));

        String toReturn=Passages.get(place).substring(0,200);
        if(toReturn.charAt(199)==32)
        {
            toReturn=toReturn.strip();
            toReturn=toReturn+".";
        }
        return(toReturn);
    }
}
