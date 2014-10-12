package org.eclipse.oomph.setup.ui.questionaire;

import org.eclipse.oomph.setup.ui.questionaire.AnimatedCanvas.Animator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

/**
 * @author Eike Stepper
 */
public final class GearAnimator extends Animator
{
  public static final int PAGE_WIDTH = 620;

  public static final int PAGE_HEIGHT = 420;

  private static final int GEARS = 7;

  private static final int TEETH = 8;

  private static final float ANGLE = 360 / TEETH;

  private static final int BORDER = 30;

  private static final double RADIAN = 2 * Math.PI / 360;

  private static final int BIG_FONT_PX = 48;

  private final Font font;

  private final Font bigFont;

  private final Font hoverFont;

  private final Image logo;

  private final Page[] pages = new Page[GEARS];

  private final Path[] gearPaths = new Path[GEARS];

  private final Color[] gearBackground = new Color[2];

  private final Color[] gearForeground = new Color[2];

  private Color purple;

  private final float radius;

  private final int pageY;

  private float angle;

  private float speed;

  private boolean overflow;

  private int selection;

  private int oldSelection = -1;

  private int hover = -1;

  private int oldHover = -1;

  private Image pageBuffer;

  private Image oldPageBuffer;

  private boolean pageBufferUpdated;

  public GearAnimator(final Display display, Font font)
  {
    super(display);
    this.font = font;
    bigFont = createFont(display, font, BIG_FONT_PX);
    hoverFont = createFont(display, font, BIG_FONT_PX + 6);
    logo = new Image(display, "questionaire/logo.png");

    radius = 32;
    setSize((int)(GEARS * 2 * radius), (int)(2 * radius));
    pageY = getHeight() + 2 * BORDER;

    // Not selected.
    gearBackground[0] = new Color(display, 169, 171, 202);
    gearForeground[0] = new Color(display, 140, 132, 171);

    // Selected.
    gearBackground[1] = new Color(display, 247, 148, 30);
    gearForeground[1] = new Color(display, 207, 108, 0);

    purple = new Color(display, 43, 34, 84);

    class TestPage extends Page
    {
      private final Image image;

      public TestPage(int index, String title, String... answers)
      {
        super(index, title, answers);

        if (index != 0)
        {
          image = new Image(display, "questionaire/page" + index + ".png");
        }
        else
        {
          image = null;
        }
      }

      public TestPage(int index, String title)
      {
        this(index, title, "Yes, Please", "No, Thanks");
      }

      @Override
      protected void dispose()
      {
        if (image != null)
        {
          image.dispose();
        }
      }

      @Override
      protected int onMouseMove(int x, int y)
      {
        int i = getAnswer(x, y);
        if (i != -1)
        {
          pageBufferUpdated = false;
          return -2 - i;
        }

        if (hover < -1)
        {
          pageBufferUpdated = false;
        }

        return super.onMouseMove(x, y);
      }

      @Override
      protected boolean onMouseDown(int x, int y)
      {
        int i = getAnswer(x, y);
        if (i != -1)
        {
          setChoice(i);
          updatePage();
          setSelection(getSelection() + 1);
          return true;
        }

        return super.onMouseDown(x, y);
      }

      @Override
      protected void draw(GC gc)
      {
        String title = getTitle();
        String[] answers = getAnswers();
        Rectangle[] answerBoxes = getAnswerBoxes();

        Font oldFont = gc.getFont();
        gc.setFont(bigFont);

        Point extent = gc.stringExtent(title);
        gc.setForeground(purple);
        gc.drawText(title, (PAGE_WIDTH - extent.x) / 2, 0, true);

        if (image != null)
        {
          Rectangle bounds = image.getBounds();
          gc.drawImage(image, (PAGE_WIDTH - bounds.width) / 2, (PAGE_HEIGHT - bounds.height) / 2);
        }

        double answerWidth = PAGE_WIDTH / answers.length;
        for (int i = 0; i < answers.length; i++)
        {
          String answer = answers[i];
          boolean hovered = false;
          if (-2 - i == hover)
          {
            oldHover = hover;
            hovered = true;
          }

          gc.setFont(hovered ? hoverFont : bigFont);
          gc.setForeground(i == getChoice() ? purple : display.getSystemColor(SWT.COLOR_DARK_GRAY));

          extent = gc.stringExtent(answer);
          int x = (int)((i + .5) * answerWidth - extent.x / 2);
          int y = PAGE_HEIGHT - extent.y;
          gc.drawText(answer, x, y, true);

          answerBoxes[i] = new Rectangle(x, y, extent.x, extent.y);
        }

        gc.setFont(oldFont);
      }

      private int getAnswer(int x, int y)
      {
        x -= BORDER;
        y -= pageY;

        String[] answers = getAnswers();
        Rectangle[] answerBoxes = getAnswerBoxes();

        for (int i = 0; i < answers.length; i++)
        {
          Rectangle box = answerBoxes[i];
          if (box.contains(x, y))
          {
            return i;
          }
        }

        return -1;
      }
    }

    pages[0] = new TestPage(0, "Welcome to Eclipse Oomph", "Proceed", "Cancel");
    pages[1] = new TestPage(1, "Refresh Resources Automatically?");
    pages[2] = new TestPage(2, "Show Line Numbers in Editors?");
    pages[3] = new TestPage(3, "Check Spelling in Text Editors?");
    pages[4] = new TestPage(4, "Execute Jobs in Background?");
    pages[5] = new TestPage(5, "Encode Text Files with UTF-8?");
    pages[6] = new TestPage(6, "Enable Preference Recorder?");
  }

  @Override
  public void dispose()
  {
    for (Path path : gearPaths)
    {
      if (path != null)
      {
        path.dispose();
      }
    }

    for (Page page : pages)
    {
      if (page != null)
      {
        page.dispose();
      }
    }

    purple.dispose();
    gearForeground[0].dispose();
    gearBackground[0].dispose();
    gearForeground[1].dispose();
    gearBackground[1].dispose();

    hoverFont.dispose();
    bigFont.dispose();
    font.dispose();
    logo.dispose();
    super.dispose();
  }

  private Font createFont(Display display, Font baseFont, int pixelHeight)
  {
    GC fontGC = new GC(display);

    try
    {
      FontData[] fontData = baseFont.getFontData();
      int fontSize = 40;
      while (fontSize > 0)
      {
        for (int i = 0; i < fontData.length; i++)
        {
          fontData[i].setHeight(fontSize);
          fontData[i].setStyle(SWT.BOLD);
        }

        Font font = new Font(display, fontData);
        fontGC.setFont(font);
        int height = fontGC.stringExtent("Ag").y;
        if (height <= pixelHeight)
        {
          return font;
        }

        font.dispose();
        --fontSize;
      }

      throw new RuntimeException("Could not create a big font");
    }
    finally
    {
      fontGC.dispose();
    }
  }

  public void restart()
  {
    angle = 0;
    speed = 0;
  }

  public int getSelection()
  {
    return selection;
  }

  public void setSelection(int selection)
  {
    if (selection < 0)
    {
      selection = 0;
      overflow = true;
    }
    else if (selection > GEARS - 1)
    {
      selection = GEARS - 1;
      overflow = true;
    }

    if (overflow)
    {
      overflow = false;
      while (advance())
      {
      }

      overflow = true;
      return;
    }

    oldSelection = this.selection;
    this.selection = selection;

    Image tmpPageBuffer = oldPageBuffer;
    oldPageBuffer = pageBuffer;
    pageBuffer = tmpPageBuffer;
    pageBufferUpdated = false;

    restart();
  }

  public int getOldSelection()
  {
    return oldSelection;
  }

  @Override
  protected boolean onMouseMove(int x, int y)
  {
    if (x != Integer.MIN_VALUE && y != Integer.MIN_VALUE)
    {
      GC gc = getBufferGC();
      if (gc != null)
      {
        for (int i = 0; i < gearPaths.length; i++)
        {
          Path path = gearPaths[i];
          if (path != null && path.contains(x, y, gc, false))
          {
            if (i != hover)
            {
              hover = i;
            }

            return true;
          }
        }

        Page page = pages[selection];
        if (page != null)
        {
          hover = page.onMouseMove(x, y);
          if (hover < -1)
          {
            return true;
          }
        }
      }
    }

    hover = -1;
    return false;
  }

  @Override
  protected boolean onMouseDown(int x, int y)
  {
    if (x != Integer.MIN_VALUE && y != Integer.MIN_VALUE)
    {
      GC gc = getBufferGC();
      if (gc != null)
      {
        for (int i = 0; i < gearPaths.length; i++)
        {
          Path path = gearPaths[i];
          if (path != null && path.contains(x, y, gc, false))
          {
            if (i != getSelection())
            {
              setSelection(i);
            }

            return true;
          }
        }

        Page page = pages[selection];
        if (page != null)
        {
          if (page.onMouseDown(x, y))
          {
            return true;
          }
        }
      }
    }

    return false;
  }

  @Override
  protected boolean advance()
  {
    boolean needsRedraw = false;
    if (overflow)
    {
      overflow = false;
      needsRedraw = true;
    }

    if (hover != oldHover)
    {
      needsRedraw = true;
    }

    if (speed >= ANGLE)
    {
      return needsRedraw;
    }

    needsRedraw = true;
    speed += .4f;
    angle += speed;
    return needsRedraw;
  }

  @Override
  protected void paint(Image buffer, GC gc)
  {
    Display display = getDisplay();
    gc.setFont(font);
    gc.setLineWidth(3);
    gc.setAntialias(SWT.ON);

    for (int i = 0; i < GEARS; i++)
    {
      if (i != selection)
      {
        paint(gc, display, i);
      }
    }

    paint(gc, display, selection);

    if (!pageBufferUpdated)
    {
      updatePage();
      pageBufferUpdated = true;
    }

    int alpha = Math.min((int)(255 * speed / ANGLE), 255);

    if (oldSelection == -1)
    {
      gc.setAlpha(alpha);
      gc.drawImage(pageBuffer, BORDER, pageY);
      gc.setAlpha(255);
    }
    else
    {
      double progress = 2 * speed / ANGLE;
      int slide = Math.min((int)(PAGE_WIDTH * progress * progress), PAGE_WIDTH);

      gc.setAlpha(255 - alpha);
      if (selection > oldSelection)
      {
        gc.drawImage(oldPageBuffer, slide, 0, PAGE_WIDTH - slide, PAGE_HEIGHT, BORDER, pageY, PAGE_WIDTH - slide, PAGE_HEIGHT);
        gc.setAlpha(alpha);
        gc.drawImage(pageBuffer, 0, 0, slide, PAGE_HEIGHT, BORDER + PAGE_WIDTH - slide, pageY, slide, PAGE_HEIGHT);
      }
      else
      {
        gc.drawImage(oldPageBuffer, 0, 0, PAGE_WIDTH - slide, PAGE_HEIGHT, BORDER + slide, pageY, PAGE_WIDTH - slide, PAGE_HEIGHT);
        gc.setAlpha(alpha);
        gc.drawImage(pageBuffer, PAGE_WIDTH - slide, 0, slide, PAGE_HEIGHT, BORDER, pageY, slide, PAGE_HEIGHT);
      }

      gc.setAlpha(255);
    }

    gc.drawImage(logo, BORDER + PAGE_WIDTH - logo.getBounds().width, BORDER);
  }

  private void paint(GC gc, Display display, int i)
  {
    double offset = 2 * i * radius;
    double x = BORDER + radius + offset;
    double y = BORDER + radius;
    double r2 = (double)radius * .8f;
    double r3 = (double)radius * .5f;

    int selected = 0;
    double factor = 1;
    if (i == oldSelection)
    {
      if (speed < ANGLE / 2)
      {
        selected = 1;
      }
    }
    else if (i == selection)
    {
      if (speed >= ANGLE / 2)
      {
        selected = 1;
        factor += (ANGLE - speed) * .02;
      }
      else
      {
        factor += speed * .02;
      }
    }

    boolean hovered = false;
    if (i == hover)
    {
      factor += .1;
      oldHover = hover;
      if (selected == 0)
      {
        hovered = true;
      }
    }
    else if (hover == -1)
    {
      oldHover = -1;
    }

    double outerR = factor * radius;
    double innerR = factor * r2;
    float angleOffset = (angle + i * ANGLE) * (i % 2 == 1 ? -1 : 1);
    gc.setForeground(hovered ? display.getSystemColor(SWT.COLOR_DARK_GRAY) : gearForeground[selected]);
    gc.setBackground(hovered ? display.getSystemColor(SWT.COLOR_GRAY) : gearBackground[selected]);
    Path path = drawGear(gc, x, y, outerR, innerR, angleOffset);

    if (gearPaths[i] != null)
    {
      gearPaths[i].dispose();
    }

    gearPaths[i] = path;

    int ovalX = (int)(x - factor * r3);
    int ovalY = (int)(y - factor * r3);
    int ovalR = (int)(2 * factor * r3);
    gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
    gc.fillOval(ovalX, ovalY, ovalR, ovalR);
    gc.drawOval(ovalX, ovalY, ovalR, ovalR);

    String number = Integer.toString(i);
    Point extent = gc.stringExtent(number);

    if (selected == 1)
    {
      gc.setForeground(gearForeground[1]);
    }
    else
    {
      Page page = pages[i];
      if (page != null && page.getChoice() != -1)
      {
        gc.setForeground(purple);
      }
      else
      {
        gc.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
      }
    }

    gc.drawText(number, (int)(x - extent.x / 2), (int)(y - 2 - extent.y / 2), true);
  }

  private Path drawGear(GC gc, double cx, double cy, double outerR, double innerR, float angleOffset)
  {
    double radian2 = ANGLE / 2 * RADIAN;
    double radian3 = .06;

    Display display = getDisplay();
    Path path = new Path(display);

    for (int i = 0; i < TEETH; i++)
    {
      double radian = (i * ANGLE + angleOffset) * RADIAN;

      double x = cx + outerR * Math.cos(radian);
      double y = cy - outerR * Math.sin(radian);

      if (i == 0)
      {
        path.moveTo((int)x, (int)y);
      }

      double r1 = radian + radian3;
      double r3 = radian + radian2;
      double r2 = r3 - radian3;
      double r4 = r3 + radian2;

      x = cx + innerR * Math.cos(r1);
      y = cy - innerR * Math.sin(r1);
      path.lineTo((int)x, (int)y);

      x = cx + innerR * Math.cos(r2);
      y = cy - innerR * Math.sin(r2);
      path.lineTo((int)x, (int)y);

      x = cx + outerR * Math.cos(r3);
      y = cy - outerR * Math.sin(r3);
      path.lineTo((int)x, (int)y);

      x = cx + outerR * Math.cos(r4);
      y = cy - outerR * Math.sin(r4);
      path.lineTo((int)x, (int)y);
    }

    path.close();
    gc.fillPath(path);
    gc.drawPath(path);
    return path;
  }

  private void updatePage()
  {
    Display display = getDisplay();
    if (pageBuffer == null)
    {
      pageBuffer = new Image(display, PAGE_WIDTH, PAGE_HEIGHT);
    }

    GC gc = new GC(pageBuffer);
    gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
    gc.fillRectangle(pageBuffer.getBounds());

    Page page = pages[selection];
    if (page != null)
    {
      page.draw(gc);
    }

    gc.dispose();
  }

  /**
   * @author Eike Stepper
   */
  public abstract class Page
  {
    private final int index;

    private final String title;

    private final String[] answers;

    private final Rectangle[] answerBoxes;

    private int choice = -1;

    public Page(int index, String title, String... answers)
    {
      this.index = index;
      this.title = title;
      this.answers = answers;
      answerBoxes = new Rectangle[answers.length];
    }

    public final int getIndex()
    {
      return index;
    }

    public final String getTitle()
    {
      return title;
    }

    public final String[] getAnswers()
    {
      return answers;
    }

    public final Rectangle[] getAnswerBoxes()
    {
      return answerBoxes;
    }

    public final int getChoice()
    {
      return choice;
    }

    public final void setChoice(int choice)
    {
      this.choice = choice;
    }

    protected void dispose()
    {
    }

    protected int onMouseMove(int x, int y)
    {
      return -1;
    }

    protected boolean onMouseDown(int x, int y)
    {
      return false;
    }

    protected abstract void draw(GC gc);
  }
}