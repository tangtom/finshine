package com.incito.finishe.pulllistview;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;


/**
 * A subclass of {@link android.widget.CursorAdapter} that provides
 * reordering of the elements in the Cursor based on completed
 * drag-sort operations. The reordering is a simple mapping of
 * list positions into Cursor positions (the Cursor is unchanged).
 * To persist changes made by drag-sorts, one can retrieve the
 * mapping with the {@link #getCursorPositions()} method, which
 * returns the reordered list of Cursor positions.
 *
 * An instance of this class is passed
 * to {@link DragSortListView#setAdapter(ListAdapter)} and, since
 * this class implements the {@link DragSortListView.DragSortListener}
 * interface, it is automatically set as the DragSortListener for
 * the DragSortListView instance.
 */
public abstract class AnimationCursorAdapter extends CursorAdapter {

    public static final int REMOVED = -1;

    /**
     * Key is ListView position, value is Cursor position
     */
    private SparseIntArray mListMapping = new SparseIntArray();

    protected ArrayList<Integer> mRemovedCursorPositions = new ArrayList<Integer>();
    
    @SuppressWarnings("deprecation")
	public AnimationCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public AnimationCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public AnimationCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Swaps Cursor and clears list-Cursor mapping.
     *
     * @see android.widget.CursorAdapter#swapCursor(android.database.Cursor)
     */
    @Override
    public Cursor swapCursor(Cursor newCursor) {
        Cursor old = super.swapCursor(newCursor);
        resetMappings();
        return old;
    }

    /**
     * Changes Cursor and clears list-Cursor mapping.
     *
     * @see android.widget.CursorAdapter#changeCursor(android.database.Cursor)
     */
    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
        resetMappings();
    }

    /**
     * Resets list-cursor mapping.
     */
    public void reset() {
        resetMappings();
        notifyDataSetChanged();
    }

    private void resetMappings() {
        mListMapping.clear();
        mRemovedCursorPositions.clear();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(mListMapping.get(position, position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(mListMapping.get(position, position));
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(mListMapping.get(position, position), convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(mListMapping.get(position, position), convertView, parent);
    }

    public void remove(int which) {
        int cursorPos = mListMapping.get(which, which);
        if (!mRemovedCursorPositions.contains(cursorPos)) {
            mRemovedCursorPositions.add(cursorPos);
        }

        int newCount = getCount();
        for (int i = which; i < newCount; ++i) {
            mListMapping.put(i, mListMapping.get(i + 1, i + 1));
        }
        mListMapping.delete(newCount);
        cleanMapping();
        notifyDataSetChanged();
        if(callBack != null){
        	callBack.remove(which);
        }
    }
    
    private removeCallBack callBack;
    private interface removeCallBack{
    	public void remove(int index);
    }
    public void setRemoveCallBack(removeCallBack _callBack){
    	this.callBack =_callBack;
    }

    /**
     * Remove unnecessary mappings from sparse array.
     */
    private void cleanMapping() {
        ArrayList<Integer> toRemove = new ArrayList<Integer>();

        int size = mListMapping.size();
        for (int i = 0; i < size; ++i) {
            if (mListMapping.keyAt(i) == mListMapping.valueAt(i)) {
                toRemove.add(mListMapping.keyAt(i));
            }
        }

        size = toRemove.size();
        for (int i = 0; i < size; ++i) {
            mListMapping.delete(toRemove.get(i));
        }
    }
    
    @Override
    public int getCount() {
        return super.getCount() - mRemovedCursorPositions.size();
    }

    /**
     * Get the Cursor position mapped to by the provided list position
     * (given all previously handled drag-sort
     * operations).
     *
     * @param position List position
     *
     * @return The mapped-to Cursor position
     */
    public int getCursorPosition(int position) {
        return mListMapping.get(position, position);
    }
}
