
def split(delimiters, text):
    """
    Splits a string using all the delimiters supplied as input string
    :param delimiters:
    :param text: string containing delimiters to use to split the string, e.g. `,;? `
    :return: a list of words from splitting text using the delimiters
    """

    import re
    regex_pattern = '|'.join(map(re.escape, delimiters))
    return re.split(regex_pattern, text, 0)


def get_alphabet_characters():
    return ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']


def convert_to_word_list(text):
    text = text.lower()
    word_list = split(" .,;?", text)
    while word_list.count('') > 0:
        word_list.remove('')
    return word_list



def words_longer_than(length, text):
    word_list = convert_to_word_list(text)
    word_list_remove = []
    for i in word_list:
        if len(i) < length:
            word_list_remove.append(i)
    for i in word_list_remove:
        word_list.remove(i)
    return word_list



def words_lengths_map(text):
    dict_lengths = dict()
    i = 0
    while len(words_longer_than(i, text)) > 0:
        dict_lengths.update({i: len(words_longer_than(i, text)) - len(words_longer_than(i + 1, text))})
        if len(words_longer_than(i, text)) - len(words_longer_than(i + 1, text)) == 0:
            dict_lengths.popitem()
        i += 1      
    return dict_lengths



def letters_count_map(text):
    text = text.lower()
    alph = get_alphabet_characters()
    val = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    for i in text:
        for j in range(len(alph)):
            if i == alph[j]:
                val[j] += 1
    map_alph = {}
    for i in range(len(alph)):
        map_alph.update({alph[i]: val[i]})
    return map_alph


def most_used_character(text):
    if len(text) == 0:
        return
    alph = get_alphabet_characters()
    map_letters = letters_count_map(text)
    temp = 0
    for i in alph:
        val = map_letters.get(i)
        if val > temp:
            temp = val
    for i in alph:
        val = map_letters.get(i)
        if val == temp:
            return i