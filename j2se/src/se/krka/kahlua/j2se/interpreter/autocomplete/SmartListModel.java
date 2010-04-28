/*
 Copyright (c) 2010 Kristofer Karlsson <kristofer.karlsson@gmail.com>

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

package se.krka.kahlua.j2se.interpreter.autocomplete;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SmartListModel<T> extends AbstractListModel {
    private final List<T> delegate = new ArrayList<T>();
    private static final int MAX_SIZE = 500;

    @Override
    public int getSize() {
        return delegate.size();
    }

    @Override
    public T getElementAt(int index) {
        return delegate.get(index);
    }

    public void clear() {
        int oldSize = getSize();
        if (oldSize != 0) {
            delegate.clear();
            this.fireIntervalRemoved(this, 0, oldSize - 1);
        }
    }

    public void setContent(Collection<T> matches) {
        if (getSize() > 0) {
            this.fireIntervalRemoved(this, 0, getSize() - 1);
        }
        delegate.clear();
        if (matches.size() >= MAX_SIZE) {
            Iterator<T> iterator = matches.iterator();
            for (int i = 0; i < MAX_SIZE; i++) {
                delegate.add(iterator.next());
            }
        } else {
            delegate.addAll(matches);
        }
        if (getSize() > 0) {
            this.fireIntervalAdded(this, 0, getSize() - 1);
        }
    }
}