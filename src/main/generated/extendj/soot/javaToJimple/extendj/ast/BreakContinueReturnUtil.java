package soot.javaToJimple.extendj.ast;

import java.util.*;


class BreakContinueReturnUtil {
  static void spliceUnprotectedStmt(Stmt                        self
                                   ,Body                        b
                                   ,ArrayList<soot.jimple.Stmt> list
                                   ,soot.jimple.Stmt            stmt) {
    final boolean isInSyncBlock = isInSyncBlk(self);
    if (!isInSyncBlock)
      endExceptionRange(b, list, self);

    for (Iterator<FinallyHost> i = self.finallyIterator(); i.hasNext();)
      i.next().emitFinallyCode(b);

    if (isInSyncBlock)
      endExceptionRange(b, list, self);

    //b.setLine(this);
    b.add(stmt);
    beginExceptionRange(b, list);
  }

  static boolean isInSyncBlk(Stmt stmt) {
    Iterator<FinallyHost> i = stmt.finallyIterator();
    return i.hasNext() && i.next() instanceof SynchronizedStmt;
  }

  static void beginExceptionRange(Body b, ArrayList<soot.jimple.Stmt> list) {
    if (list == null) return;

    assert b.HACK_singleAddStmtListener == null;
    b.HACK_singleAddStmtListener = list::add;
  }

  static void endExceptionRange(Body b, ArrayList<soot.jimple.Stmt> list, ASTNode lbl_end_loc) {
    if (list == null) return;

    list.add(b.addNewLabel(lbl_end_loc).stmt);
    //list.add(b.chains.lastElement().getLast());
  }
}