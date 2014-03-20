import sys, itertools, collections, time
def unordered_hash(key):
  primes = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101]
  total = 1
  for i in range(len(key)):
    total *= primes[ord(key.lower()[i]) - 97]
  return total

class WordsDict(collections.UserDict):
  def __getitem__(self, key):
    return self.data[unordered_hash(key)]
  def __setitem__(self, key, value):
    if unordered_hash(key) in self.data.keys():
      self.data[unordered_hash(key)].add(value)
    else:
      self.data[unordered_hash(key)] = {value}

def get_dict(key_len):
  print(time.process_time())
  word_dict = WordsDict()
  print(time.process_time())
  with open("/home/christian/cracklib-small") as dict_file:
    for i in dict_file.readlines():
      if len(i.strip()) != key_len or not i.strip().isalpha():
        continue
      else:
        word_dict[i.strip()] = i.strip()
  print(time.process_time())
  return word_dict

def main():
  dictionary = get_dict(int(sys.argv[2]))
  solutions = set()
  for word in itertools.combinations(str(sys.argv[1]), int(sys.argv[2])):
    if ''.join(word) in solutions:
      continue
    if unordered_hash(''.join(word)) in dictionary.keys():
      solutions.update(dictionary[''.join(word)])
  print(solutions)


if __name__=='__main__':
  main()
